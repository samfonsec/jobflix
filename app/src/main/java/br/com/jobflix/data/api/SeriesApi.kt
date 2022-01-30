package br.com.jobflix.data.api

import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.model.Serie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET(SHOWS_API)
    suspend fun getShows(@Query("page") page: Int): List<Serie>

    @GET(EPISODES_API)
    suspend fun getEpisodes(@Path("id") serieId: Int): List<Episode>

    companion object {
        private const val SHOWS_API = "shows"

        private const val EPISODES_API = "shows/{id}/episodes"
    }
}