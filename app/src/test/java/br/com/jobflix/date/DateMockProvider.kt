package br.com.jobflix.date

import java.util.Calendar
import java.util.Calendar.APRIL
import java.util.Calendar.OCTOBER
import java.util.Date

object DateMockProvider {

    val mockedDate: Date = Calendar.getInstance().apply {
        set(1993, OCTOBER, 13)
    }.time

    val mockedDate2: Date = Calendar.getInstance().apply {
        set(1993, APRIL, 13)
    }.time

    val mockedDate3: Date = Calendar.getInstance().apply {
        set(1993, APRIL, 31)
    }.time
}