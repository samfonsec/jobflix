package br.com.jobflix.data.dataSource

import br.com.jobflix.data.api.PeopleApi
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.PeopleRepository

class PeopleDataSource(private val api: PeopleApi) : PeopleRepository {

    override suspend fun getPeopleSeries(id: Int): ResultStatus<List<Serie>> {
        return try {
            ResultStatus.Success(api.getPeopleShows(id).map { it._embedded.show })
        } catch (e: Exception) {
            ResultStatus.Error(e)
        }
    }
}