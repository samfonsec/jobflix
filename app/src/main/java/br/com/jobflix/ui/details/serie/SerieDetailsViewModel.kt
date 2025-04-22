package br.com.jobflix.ui.details.serie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository
import kotlinx.coroutines.launch

class SerieDetailsViewModel(
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val _onEpisodesResult = MutableLiveData<Map<Int, List<Episode>>>()
    val onEpisodesResult: LiveData<Map<Int, List<Episode>>> = _onEpisodesResult

    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError

    private val _onLoading = MutableLiveData<Boolean>()
    val onLoading: LiveData<Boolean> = _onLoading

    private val _onCheckFavorite = MutableLiveData<Boolean>()
    val onCheckFavorite: LiveData<Boolean> = _onCheckFavorite

    fun loadEpisodes(serieId: Int) {
        _onLoading.postValue(true)
        viewModelScope.launch {
            seriesRepository.getEpisodes(serieId).let { result ->
                when (result) {
                    is ResultStatus.Success -> _onEpisodesResult.postValue(result.data.groupBy { it.season })
                    is ResultStatus.Error -> _onError.postValue(true)
                }
            }
            _onLoading.postValue(false)
        }
    }

    fun saveAsFavorite(serie: Serie) {
        viewModelScope.launch { seriesRepository.saveFavorite(serie) }
    }

    fun removeFavorite(serie: Serie) {
        viewModelScope.launch { seriesRepository.removeFavorite(serie) }
    }

    fun checkIfIsFavorite(serieId: Int) {
        viewModelScope.launch {
            seriesRepository.isFavorite(serieId).let { result ->
                when (result) {
                    is ResultStatus.Success -> {
                        _onCheckFavorite.postValue(result.data)
                    }

                    is ResultStatus.Error -> _onError.postValue(true)
                }
            }
        }
    }
}