package br.com.jobflix.data.dataSource

import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.api.SearchApi
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SearchRepository

class SearchDataSource(private val api: SearchApi) : SearchRepository {

    override suspend fun searchSeries(query: String): ResultStatus<List<Serie>> {
        return try {
            ResultStatus.Success(api.searchShows(query).map { it.show })
        } catch (e: Exception) {
            ResultStatus.Error(e)
        }
    }
}