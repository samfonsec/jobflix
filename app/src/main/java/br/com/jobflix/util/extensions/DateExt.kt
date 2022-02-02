package br.com.jobflix.util.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(this)

fun Date.format(): String = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(this)

fun Date.age(): Int {
    val birthDate = Calendar.getInstance().also { it.time = this }
    val today = Calendar.getInstance()
    var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR))
        age--

    return age
}