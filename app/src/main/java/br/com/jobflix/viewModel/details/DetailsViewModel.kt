package br.com.jobflix.viewModel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ApiResult
import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val seriesRepository: SeriesRepository
) : BaseViewModel() {

    private val onEpisodesResult = MutableLiveData<Map<Int, List<Episode>>>()
    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()

    fun onEpisodesResult(): LiveData<Map<Int, List<Episode>>> = onEpisodesResult
    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading

    fun loadEpisodes(serieId: Int) {
        onLoading.postValue(true)
        launch {
            seriesRepository.getEpisodes(serieId).let { result ->
                when (result) {
                    is ApiResult.Success -> {
                        onEpisodesResult.postValue(result.data.groupBy { it.season })
                    }
                    is ApiResult.Error -> onError.postValue(true)
                }
            }
            onLoading.postValue(false)
        }
    }
}