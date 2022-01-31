package br.com.jobflix.data.repository

import br.com.jobflix.data.model.Serie
import br.com.jobflix.data.api.ResultStatus
import br.com.jobflix.data.model.Episode

interface SeriesRepository {

    suspend fun getSeries(page: Int): ResultStatus<List<Serie>>

    suspend fun getEpisodes(serieId: Int): ResultStatus<List<Episode>>

    suspend fun getFavorites(): ResultStatus<List<Serie>>

    suspend fun saveFavorite(serie: Serie)

    suspend fun removeFavorite(serie: Serie)

    suspend fun isFavorite(serieId: Int): ResultStatus<Boolean>
}