package br.com.jobflix.data.repository

import br.com.jobflix.data.api.ApiResult
import br.com.jobflix.data.model.Serie

interface SearchRepository {

    suspend fun searchSeries(query: String): ApiResult<List<Serie>>
}