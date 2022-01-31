package br.com.jobflix.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.People
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class PeopleSearchViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private val onSearch = MutableLiveData<List<People>>()
    private val onError = MutableLiveData<Boolean>()

    fun onSearch(): LiveData<List<People>> = onSearch
    fun onError(): LiveData<Boolean> = onError

    fun searchForPeople(query: String) {
        launch {
            searchRepository.searchPeople(query).let { result ->
                when (result) {
                    is ResultStatus.Success -> onSearch.postValue(result.data)
                    is ResultStatus.Error -> onError.postValue(true)
                }
            }
        }
    }
}