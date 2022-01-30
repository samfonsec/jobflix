package br.com.jobflix.data.repository

import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie

interface SearchRepository {

    suspend fun searchSeries(query: String): ResultStatus<List<Serie>>
}