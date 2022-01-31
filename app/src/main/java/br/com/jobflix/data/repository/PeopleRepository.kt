package br.com.jobflix.data.repository

import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.People
import br.com.jobflix.data.model.Serie

interface PeopleRepository {

    suspend fun getPeopleSeries(id: Int): ResultStatus<List<Serie>>
}