package br.com.jobflix.viewModel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.PeopleRepository
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class PeopleDetailsViewModel(
    private val peopleRepository: PeopleRepository
) : BaseViewModel() {

    private val onSeriesResult = MutableLiveData<List<Serie>>()
    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()

    fun onSeriesResult(): LiveData<List<Serie>> = onSeriesResult
    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading

    fun loadPeopleSeries(peopleId: Int) {
        onLoading.postValue(true)
        launch {
            peopleRepository.getPeopleSeries(peopleId).let { result ->
                when (result) {
                    is ResultStatus.Success -> onSeriesResult.postValue(result.data)
                    is ResultStatus.Error -> onError.postValue(true)
                }
            }
            onLoading.postValue(false)
        }
    }
}