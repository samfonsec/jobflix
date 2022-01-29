package br.com.jobflix.data.model

import android.os.Parcelable
import br.com.jobflix.util.Constants.EPISODE_RUNTIME_FORMAT
import br.com.jobflix.util.Constants.SEASON_EPISODE_FORMAT
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val runtime: Int,
    val image: Image?,
    val summary: String,
) : Parcelable {

    fun formattedNumber() = String.format(SEASON_EPISODE_FORMAT, season, number)

    fun formattedRuntime() = String.format(EPISODE_RUNTIME_FORMAT, runtime)
}