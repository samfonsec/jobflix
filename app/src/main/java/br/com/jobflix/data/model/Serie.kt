package br.com.jobflix.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.jobflix.util.Constants.FAVORITES_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FAVORITES_TABLE_NAME)
data class Serie(
    @PrimaryKey
    val id: Int,
    val name: String,
    val genres: List<String>,
    val premiered: String?,
    @Embedded
    val rating: Rating?,
    @Embedded
    val image: Image?,
    val summary: String?,
    @Embedded
    val schedule: Schedule?
) : Parcelable {

    fun formattedGenres() = genres.joinToString(" | ")

    fun scheduleDays() = schedule?.days?.joinToString(", ")

    fun premiereYear() = premiered?.split("-")?.firstOrNull() ?: premiered
}