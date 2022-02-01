package br.com.jobflix.date

import java.util.*

object DateMockProvider {

    private const val JANUARY = 0
    private const val OCTOBER = 9

    val mockedDate: Date = Calendar.getInstance().apply {
        set(1993, OCTOBER, 13)
    }.time

    val mockedDate2: Date = Calendar.getInstance().apply {
        set(1993, JANUARY, 13)
    }.time

    val mockedDate3: Date = Calendar.getInstance().apply {
        set(1993, JANUARY, 31)
    }.time
}