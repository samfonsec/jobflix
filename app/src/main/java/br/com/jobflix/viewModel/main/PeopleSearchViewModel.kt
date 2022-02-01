package br.com.jobflix.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.People
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.util.extensions.isCanceling
import br.com.jobflix.viewModel.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PeopleSearchViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private val onSearch = MutableLiveData<List<People>>()
    private val onError = MutableLiveData<Boolean>()
    private var currentJob: Job? = null

    fun onSearch(): LiveData<List<People>> = onSearch
    fun onError(): LiveData<Boolean> = onError

    fun searchForPeople(query: String) {
        currentJob?.cancel()
        currentJob = launch {
            searchRepository.searchPeople(query).let { result ->
                when (result) {
                    is ResultStatus.Success -> onSearch.postValue(result.data)
                    is ResultStatus.Error -> onError.postValue(result.exception.isCanceling())

                }
            }
        }
    }

    fun cancelLastJob() = currentJob?.cancel()
}