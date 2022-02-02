package br.com.jobflix.data.repository

import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.People
import br.com.jobflix.data.model.Serie

interface SearchRepository {

    suspend fun searchSeries(query: String): ResultStatus<List<Serie>>

    suspend fun searchPeople(query: String): ResultStatus<List<People>>
}