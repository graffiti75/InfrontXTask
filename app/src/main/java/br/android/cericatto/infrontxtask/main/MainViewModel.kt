package br.android.cericatto.infrontxtask.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.android.cericatto.infrontxtask.data.result.ResultItem
import br.android.cericatto.infrontxtask.util.DispatcherProvider
import br.android.cericatto.infrontxtask.util.Resource
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
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class UIEvent {
        class Success(val results: List<ResultItem>): UIEvent()
        class Failure(val errorText: String): UIEvent()
        object Loading : UIEvent()
        object Empty : UIEvent()
    }

    private val _data = MutableStateFlow<UIEvent>(UIEvent.Empty)
    val data: StateFlow<UIEvent> = _data

    fun getData() {
        viewModelScope.launch(dispatchers.io) {
            _data.value = UIEvent.Loading
            when(val apiResponse = repository.getResults()) {
                is Resource.Error ->
                    _data.value = UIEvent.Failure(apiResponse.message!!)
                is Resource.Success -> {
                    val result = apiResponse.data!!
                    if (result == null) {
                        _data.value = UIEvent.Failure("Unexpected error")
                    } else {
                        _data.value = UIEvent.Success(result)
                    }
                }
            }
        }
    }
}