package br.com.jobflix.data.dataSource

import br.com.jobflix.data.api.ApiResult
import br.com.jobflix.data.api.SearchApi
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SearchRepository

class SearchDataSource(private val api: SearchApi) : SearchRepository {

    override suspend fun searchSeries(query: String): ApiResult<List<Serie>> {
        return try {
            ApiResult.Success(api.searchShows(query).map { it.show })
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}