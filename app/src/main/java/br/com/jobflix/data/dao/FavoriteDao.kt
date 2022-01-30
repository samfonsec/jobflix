package br.com.jobflix.data.dao

import androidx.room.*
import br.com.jobflix.data.model.Serie

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM table_favorites ORDER BY id ASC")
    suspend fun getFavoriteSeries(): List<Serie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSerie(serie: Serie)

    @Delete
    suspend fun remove(serie: Serie)
}