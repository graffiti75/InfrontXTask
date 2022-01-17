package br.android.cericatto.infrontxtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.android.cericatto.infrontxtask.adapter.FixturesRecyclerViewItem
import br.android.cericatto.infrontxtask.data.fixture.FixtureItem
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.repository.MainRepository
import br.android.cericatto.infrontxtask.util.DispatcherProvider
import br.android.cericatto.infrontxtask.util.Resource
import br.android.cericatto.infrontxtask.util.filterDate
import br.android.cericatto.infrontxtask.util.toFixturesRecyclerViewItem
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
class FixtureViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class UIEvent {
        class Success(val items: List<FixturesRecyclerViewItem>): FixtureViewModel.UIEvent()
        class Failure(val errorText: String): UIEvent()
        object Loading : UIEvent()
        object Empty : UIEvent()
    }

    private val _data = MutableStateFlow<UIEvent>(UIEvent.Empty)
    val data: StateFlow<UIEvent> = _data

    fun getFixtures() {
        viewModelScope.launch(dispatchers.io) {
            _data.value = UIEvent.Loading
            when(val apiResponse = repository.getFixtures()) {
                is Resource.Error ->
                    _data.value = UIEvent.Failure(apiResponse.message!!)
                is Resource.Success -> {
                    val items: ArrayList<FixtureItem>? = apiResponse.data
                    if (items == null) {
                        _data.value = UIEvent.Failure("Unexpected error")
                    } else {
                        initFixturesRecyclerViewItem(items)
                    }
                }
            }
        }
    }

    private fun initFixturesRecyclerViewItem(items: ArrayList<FixtureItem>) {
        // ----- Algorithm to add Headers to the List -----

        // Variables setup.
        val viewItemList = mutableListOf<FixturesRecyclerViewItem>()
        val fixturesList = mutableListOf<FixtureItem>()

        // Main loop init.
        var latestMonthYear = items[0].date?.filterDate()
        fixturesList.add(items[0])

        // Main loop.
        for (i in 1 until items.size) {
            val item = items[i]
            val currentMonthYear = item.date?.filterDate()
            val lastItemReached = i == items.size - 1
            if ((currentMonthYear != latestMonthYear) || lastItemReached) {
                if (lastItemReached)
                    fixturesList.add(item)
                viewItemList.add(FixturesRecyclerViewItem.Title(latestMonthYear))
                fixturesList.forEach {
                    viewItemList.add(it.toFixturesRecyclerViewItem())
                }
                fixturesList.clear()
                fixturesList.add(item)
            } else
                fixturesList.add(item)
            latestMonthYear = item.date?.filterDate()
        }
        _data.value = UIEvent.Success(viewItemList)
    }
}