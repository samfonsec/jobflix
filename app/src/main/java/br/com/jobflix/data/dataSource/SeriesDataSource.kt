package br.com.jobflix.data.dataSource

import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.api.SeriesApi
import br.com.jobflix.data.dao.FavoriteDao
import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.repository.SeriesRepository

class SeriesDataSource(
    private val api: SeriesApi,
    private val dao: FavoriteDao,
) : SeriesRepository {

    override suspend fun getSeries(page: Int): ResultStatus<List<Serie>> {
        return try {
            ResultStatus.Success(api.getShows(page))
        } catch (e: Exception) {
            ResultStatus.Error(e)
        }
    }

    override suspend fun getEpisodes(serieId: Int): ResultStatus<List<Episode>> {
        return try {
            ResultStatus.Success(api.getEpisodes(serieId))
        } catch (e: Exception) {
            ResultStatus.Error(e)
        }
    }

    override suspend fun getFavorites(): ResultStatus<List<Serie>> {
        return try {
            ResultStatus.Success(dao.getFavoriteSeries())
        } catch (e: Exception) {
            ResultStatus.Error(e)
        }
    }

    override suspend fun saveFavorite(serie: Serie) {
        try {
            dao.saveSerie(serie)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun removeFavorite(serie: Serie) {
        try {
            dao.remove(serie)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun isFavorite(serieId: Int): ResultStatus<Boolean> {
        return try {
            ResultStatus.Success(dao.isFavorite(serieId))
        } catch (e: Exception) {
            ResultStatus.Error(e)
        }
    }
}