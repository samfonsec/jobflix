package br.com.jobflix.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Serie(
    val id: Int,
    val name: String,
    val genres: List<String>,
    val premiered: String?,
    val rating: Rating?,
    val image: Image?,
    val summary: String?,
    val schedule: Schedule?
) : Parcelable {

    fun formattedGenres() = genres.joinToString(" | ")

    fun scheduleDays() = schedule?.days?.joinToString(", ")

    fun premiereYear() = premiered?.split("-")?.firstOrNull() ?: premiered
}