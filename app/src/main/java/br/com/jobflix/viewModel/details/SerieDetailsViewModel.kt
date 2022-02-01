package br.com.jobflix.viewModel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class SerieDetailsViewModel(
    private val seriesRepository: SeriesRepository
) : BaseViewModel() {

    private val onEpisodesResult = MutableLiveData<Map<Int, List<Episode>>>()
    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()
    private val onCheckFavorite = MutableLiveData<Boolean>()

    fun onEpisodesResult(): LiveData<Map<Int, List<Episode>>> = onEpisodesResult
    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading
    fun onCheckFavorite(): LiveData<Boolean> = onCheckFavorite

    fun loadEpisodes(serieId: Int) {
        onLoading.postValue(true)
        launch {
            seriesRepository.getEpisodes(serieId).let { result ->
                when (result) {
                    is ResultStatus.Success -> onEpisodesResult.postValue(result.data.groupBy { it.season })
                    is ResultStatus.Error -> onError.postValue(true)
                }
            }
            onLoading.postValue(false)
        }
    }

    fun saveAsFavorite(serie: Serie) {
        launch { seriesRepository.saveFavorite(serie) }
    }

    fun removeFavorite(serie: Serie) {
        launch { seriesRepository.removeFavorite(serie) }
    }

    fun checkIfIsFavorite(serieId: Int) {
        launch {
            seriesRepository.isFavorite(serieId).let { result ->
                when (result) {
                    is ResultStatus.Success -> {
                        onCheckFavorite.postValue(result.data)
                    }
                    is ResultStatus.Error -> onError.postValue(true)
                }
            }
        }
    }

    fun getSeasonNumber(selectedText: String) = selectedText.split(" ")[1].toInt()
}