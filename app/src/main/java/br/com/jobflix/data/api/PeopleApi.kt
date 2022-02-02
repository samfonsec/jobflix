package br.com.jobflix.data.api

import br.com.jobflix.data.model.PeopleSerie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET(PEOPLE_SHOWS_API)
    suspend fun getPeopleShows(
        @Path("id") id: Int,
        @Query("embed") embed: String = EMBEDDED_SHOW
    ): List<PeopleSerie>

    companion object {
        private const val PEOPLE_SHOWS_API = "people/{id}/castcredits"
        private const val EMBEDDED_SHOW = "show"
    }
}