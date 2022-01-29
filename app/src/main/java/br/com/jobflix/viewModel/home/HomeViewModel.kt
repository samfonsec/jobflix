package br.com.jobflix.viewModel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.data.api.ApiResult
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val seriesRepository: SeriesRepository
) : BaseViewModel() {

    private val onSeriesResult = MutableLiveData<List<Serie>>()
    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()

    fun onSeriesResult(): LiveData<List<Serie>> = onSeriesResult
    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading

    fun loadSeries(page: Int) {
        onLoading.postValue(true)
        launch {
            seriesRepository.getSeries(page).let { result ->
                when (result) {
                    is ApiResult.Success -> onSeriesResult.postValue(result.data)
                    is ApiResult.Error -> onError.postValue(true)
                }
            }
            onLoading.postValue(false)
        }
    }
}