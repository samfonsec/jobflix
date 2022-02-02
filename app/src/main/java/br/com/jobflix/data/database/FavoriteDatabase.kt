package br.com.jobflix.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.jobflix.data.dao.FavoriteDao
import br.com.jobflix.data.model.Serie

@Database(
    entities = [Serie::class],
    version = 1
)
@TypeConverters(FavoriteTypeConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val DB_FILE_NAME = "favorite_db"

        @Volatile
        private var instance: FavoriteDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context): FavoriteDatabase {
            return Room.databaseBuilder(context, FavoriteDatabase::class.java, DB_FILE_NAME).build()
        }
    }
}