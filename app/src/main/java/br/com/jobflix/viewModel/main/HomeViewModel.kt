package br.com.jobflix.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.util.Constants.FIRST_PAGE
import br.com.jobflix.util.extensions.isCanceling
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(
    private val seriesRepository: SeriesRepository,
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private val onSeriesResult = MutableLiveData<List<Serie>>()
    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()
    private val onSearch = MutableLiveData<List<Serie>>()
    private val firstPage = arrayListOf<Serie>()
    private var currentJob: Job? = null

    fun onSeriesResult(): LiveData<List<Serie>> = onSeriesResult
    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading
    fun onSearch(): LiveData<List<Serie>> = onSearch

    fun loadSeries(page: Int) {
        onLoading.postValue(true)
        launch {
            seriesRepository.getSeries(page).let { result ->
                when (result) {
                    is ResultStatus.Success -> {
                        if (page == FIRST_PAGE)
                            firstPage += result.data

                        onSeriesResult.postValue(result.data)
                    }
                    is ResultStatus.Error -> onError.postValue(true)
                }
            }
            onLoading.postValue(false)
        }
    }

    fun searchSeries(query: String) {
        currentJob?.cancel()
        currentJob = launch {
            searchRepository.searchSeries(query).let { result ->
                when (result) {
                    is ResultStatus.Success -> onSearch.postValue(result.data)
                    is ResultStatus.Error -> onError.postValue(result.exception.isCanceling())
                }
            }
        }
    }

    fun cancelLastJob() = currentJob?.cancel()

    fun getFirstPage() = onSeriesResult.postValue(firstPage)
}