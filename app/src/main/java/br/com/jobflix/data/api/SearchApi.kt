package br.com.jobflix.data.api

import br.com.jobflix.data.model.SearchItem
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET(SEARCH_SHOWS_API)
    suspend fun searchShows(@Query("q") query: String): List<SearchItem>

    companion object {
        private const val SEARCH_SHOWS_API = "search/shows"
    }
}