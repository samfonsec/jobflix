package br.com.jobflix.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError

    private val _onLoading = MutableLiveData<Boolean>()
    val onLoading: LiveData<Boolean> = _onLoading

    private val _onFavoritesResult = MutableLiveData<List<Serie>>()
    val onFavoritesResult: LiveData<List<Serie>> = _onFavoritesResult

    fun getFavorites() {
        viewModelScope.launch {
            _onLoading.postValue(true)
            seriesRepository.getFavorites().let { result ->
                when (result) {
                    is ResultStatus.Success -> _onFavoritesResult.postValue(result.data)
                    is ResultStatus.Error -> _onError.postValue(true)
                }
                _onLoading.postValue(false)
            }
        }
    }

    fun removeFavorite(serie: Serie) {
        viewModelScope.launch {
            seriesRepository.removeFavorite(serie)
        }
    }

}