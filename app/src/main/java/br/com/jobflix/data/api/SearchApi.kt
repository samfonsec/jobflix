package br.com.jobflix.data.api

import br.com.jobflix.data.model.PeopleItem
import br.com.jobflix.data.model.SerieItem
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET(SEARCH_SHOWS_API)
    suspend fun searchShows(@Query("q") query: String): List<SerieItem>

    @GET(SEARCH_PEOPLE_API)
    suspend fun searchPeople(@Query("q") query: String): List<PeopleItem>

    companion object {
        private const val SEARCH_SHOWS_API = "search/shows"
        private const val SEARCH_PEOPLE_API = "search/people"
    }
}