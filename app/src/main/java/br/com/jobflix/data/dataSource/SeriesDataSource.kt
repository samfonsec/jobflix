package br.com.jobflix.data.dataSource

import br.com.jobflix.data.api.ApiResult
import br.com.jobflix.data.api.SeriesApi
import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository

class SeriesDataSource(private val api: SeriesApi) : SeriesRepository {

    override suspend fun getSeries(page: Int): ApiResult<List<Serie>> {
        return try {
            ApiResult.Success(api.getShows(page))
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

    override suspend fun getEpisodes(serieId: Int): ApiResult<List<Episode>> {
        return try {
            ApiResult.Success(api.getEpisodes(serieId))
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}