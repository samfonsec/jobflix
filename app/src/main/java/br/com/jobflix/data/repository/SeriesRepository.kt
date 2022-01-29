package br.com.jobflix.data.repository

import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.api.ApiResult
import br.com.jobflix.data.model.Episode

interface SeriesRepository {

    suspend fun getSeries(page: Int): ApiResult<List<Serie>>

    suspend fun getEpisodes(serieId: Int, page: Int): ApiResult<List<Episode>>
}