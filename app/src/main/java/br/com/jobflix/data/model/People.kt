package br.com.jobflix.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class People(
    val id: Int,
    val name: String,
    val birthday: String?,
    val country: Country?,
    val image: Image?
) : Parcelable