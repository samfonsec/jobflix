package br.com.jobflix.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val seriesRepository: SeriesRepository
) : BaseViewModel() {

    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()
    private val onFavoritesResult = MutableLiveData<List<Serie>>()

    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading
    fun onFavoritesResult(): LiveData<List<Serie>> = onFavoritesResult

    fun getFavorites() {
        launch {
            onLoading.postValue(true)
            seriesRepository.getFavorites().let { result ->
                when (result) {
                    is ResultStatus.Success -> {
                        onFavoritesResult.postValue(result.data.sortedBy { it.name })
                    }
                    is ResultStatus.Error -> onError.postValue(true)
                }
                onLoading.postValue(false)
            }
        }
    }

    fun removeFavorite(serie: Serie) {
        launch {
            seriesRepository.removeFavorite(serie)
        }
    }

}