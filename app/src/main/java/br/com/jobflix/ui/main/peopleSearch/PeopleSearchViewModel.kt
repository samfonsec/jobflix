package br.com.jobflix.ui.main.peopleSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.People
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.util.extensions.isCanceling
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PeopleSearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _onSearch = MutableLiveData<List<People>>()
    val onSearch: LiveData<List<People>> = _onSearch

    private val _onError = MutableLiveData<Boolean>()
    val onError: LiveData<Boolean> = _onError

    private var currentJob: Job? = null

    fun searchForPeople(query: String) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            searchRepository.searchPeople(query).let { result ->
                when (result) {
                    is ResultStatus.Success -> _onSearch.postValue(result.data)
                    is ResultStatus.Error -> _onError.postValue(result.exception.isCanceling())

                }
            }
        }
    }

    fun cancelLastJob() = currentJob?.cancel()
}