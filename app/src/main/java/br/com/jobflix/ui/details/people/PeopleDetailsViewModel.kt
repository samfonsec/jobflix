package br.com.jobflix.ui.details.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.PeopleRepository
import kotlinx.coroutines.launch

class PeopleDetailsViewModel(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    private val _onSeriesResult = MutableLiveData<List<Serie>>()
    val onSeriesResult: LiveData<List<Serie>> = _onSeriesResult

    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError

    private val _onLoading = MutableLiveData<Boolean>()
    val onLoading: LiveData<Boolean> = _onLoading

    fun loadPeopleSeries(peopleId: Int) {
        _onLoading.postValue(true)
        viewModelScope.launch {
            peopleRepository.getPeopleSeries(peopleId).let { result ->
                when (result) {
                    is ResultStatus.Success -> _onSeriesResult.postValue(result.data)
                    is ResultStatus.Error -> _onError.postValue(true)
                }
            }
            _onLoading.postValue(false)
        }
    }
}