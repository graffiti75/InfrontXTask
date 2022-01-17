package br.android.cericatto.infrontxtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.android.cericatto.infrontxtask.adapter.ResultsRecyclerViewItem
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.repository.MainRepository
import br.android.cericatto.infrontxtask.util.DispatcherProvider
import br.android.cericatto.infrontxtask.util.Resource
import br.android.cericatto.infrontxtask.util.filterDate
import br.android.cericatto.infrontxtask.util.toResultsRecyclerViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModelInject (deprecated) vs HiltViewModel
 * https://stackoverflow.com/questions/66185820/dagger-hilt-assisted-and-viewmodelinject-is-deprecated-in-dagger-hilt-view
 */
@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class UIEvent {
        class Success(val items: List<ResultItem>): UIEvent()
        class Failure(val errorText: String): UIEvent()
        object Loading : UIEvent()
        object Empty : UIEvent()
    }

    private val _data = MutableStateFlow<UIEvent>(UIEvent.Empty)
    val data: StateFlow<UIEvent> = _data

    fun getResults() {
        viewModelScope.launch(dispatchers.io) {
            _data.value = UIEvent.Loading
            when(val apiResponse = repository.getResults()) {
                is Resource.Error ->
                    _data.value = UIEvent.Failure(apiResponse.message!!)
                is Resource.Success -> {
                    val items: ArrayList<ResultItem>? = apiResponse.data
                    if (items == null) {
                        _data.value = UIEvent.Failure("Unexpected error")
                    } else {
                        initResultsRecyclerViewItem(items)
                    }
                }
            }
        }
    }

    private fun initResultsRecyclerViewItem(items: ArrayList<ResultItem>) {
        // ----- Algorithm to add Headers to the List -----
        // Variables setup.
        val viewItemList = mutableListOf<ResultsRecyclerViewItem>()
        val resultsList = mutableListOf<ResultItem>()

        // Main loop init.
        var latestMonthYear = items[0].date.filterDate()
        println("----- item[0] = ${items[0]}")
        resultsList.add(items[0])

        // Main loop.
        for (i in 1 until items.size) {
            val item = items[i]
            println("----- item[$i] = ${items[i]}")
            val currentMonthYear = item.date.filterDate()
            val lastItemReached = i == items.size - 1
            if ((currentMonthYear != latestMonthYear) || lastItemReached) {
                if (lastItemReached)
                    resultsList.add(item)
                viewItemList.add(ResultsRecyclerViewItem.MonthOfYear(latestMonthYear))
                resultsList.forEach {
                    viewItemList.add(it.toResultsRecyclerViewItem())
                }
                resultsList.clear()
                resultsList.add(item)
            } else
                resultsList.add(item)
            latestMonthYear = item.date.filterDate()
        }
        _data.value = UIEvent.Success(items)
    }
}