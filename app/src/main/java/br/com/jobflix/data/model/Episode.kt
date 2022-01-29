package br.com.jobflix.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val runtime: Int,
    val rating: Rating,
    val image: Image,
    val summary: String,
) : Parcelable