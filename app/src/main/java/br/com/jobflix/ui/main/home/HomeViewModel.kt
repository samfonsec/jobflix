package br.com.jobflix.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.util.Constants.FIRST_PAGE
import br.com.jobflix.util.Constants.KEY_AUTH_PIN
import br.com.jobflix.util.extensions.isCanceling
import br.com.jobflix.ui.auth.BaseAuthViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(
    private val seriesRepository: SeriesRepository,
    private val searchRepository: SearchRepository
) : BaseAuthViewModel() {

    private val _onSeriesResult = MutableLiveData<List<Serie>>()
    val onSeriesResult: LiveData<List<Serie>> = _onSeriesResult
    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError
    private val _onLoading = MutableLiveData<Boolean>()
    val onLoading: LiveData<Boolean> = _onLoading
    private val _onSearch = MutableLiveData<List<Serie>>()
    val onSearch: LiveData<List<Serie>> = _onSearch

    private val firstPage = arrayListOf<Serie>()
    private var currentJob: Job? = null

    fun loadSeries(page: Int) {
        _onLoading.postValue(true)
        viewModelScope.launch {
            seriesRepository.getSeries(page).let { result ->
                when (result) {
                    is ResultStatus.Success -> {
                        if (page == FIRST_PAGE)
                            firstPage += result.data

                        _onSeriesResult.postValue(result.data)
                    }
                    is ResultStatus.Error -> _onError.postValue(true)
                }
            }
            _onLoading.postValue(false)
        }
    }

    fun searchSeries(query: String) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            searchRepository.searchSeries(query).let { result ->
                when (result) {
                    is ResultStatus.Success -> _onSearch.postValue(result.data)
                    is ResultStatus.Error -> _onError.postValue(result.exception.isCanceling())
                }
            }
        }
    }

    fun cancelLastJob() = currentJob?.cancel()

    fun getFirstPage() = _onSeriesResult.postValue(firstPage)

    fun removePin() {
        sharedPreferences.edit().remove(KEY_AUTH_PIN).apply()
        enableFingerprint(false)
        skipPin()
    }
}