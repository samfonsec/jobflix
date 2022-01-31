package br.com.jobflix.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class PeopleSearchViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private val onSearch = MutableLiveData<List<Serie>>()
    private val onError = MutableLiveData<Boolean>()
    private val onLoading = MutableLiveData<Boolean>()

    fun onSearch(): LiveData<List<Serie>> = onSearch
    fun onError(): LiveData<Boolean> = onError
    fun onLoading(): LiveData<Boolean> = onLoading

    fun searchForPeople(query: String) {
        launch {
            searchRepository.searchSeries(query).let { result ->
                when (result) {
                    is ResultStatus.Success -> onSearch.postValue(result.data)
                    is ResultStatus.Error -> onError.postValue(true)
                }
            }
        }
    }
}