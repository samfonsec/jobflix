package br.com.jobflix.serie

import br.com.jobflix.serie.SerieMockProvider.mockedSerie1
import br.com.jobflix.serie.SerieMockProvider.mockedSerie2
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SerieUnitTest {


    @Test
    fun serie_genres_format_isCorrect() {
        assertEquals(mockedSerie1.formattedGenres(), "Drama | Action | Science-Fiction")
        assertEquals(mockedSerie2.formattedGenres(), "Drama")

        assertNotEquals(mockedSerie1.formattedGenres(), "| Drama | Action | Science-Fiction |")
        assertNotEquals(mockedSerie1.formattedGenres(), "Drama | Action | Science-Fiction |")
        assertNotEquals(mockedSerie2.formattedGenres(), "Drama | ")
    }

    @Test
    fun episode_schedule_days_format_isCorrect() {
        assertEquals(mockedSerie1.scheduleDays(), "Monday, Wednesday, Friday")
        assertEquals(mockedSerie2.scheduleDays(), "Monday")

        assertNotEquals(mockedSerie1.scheduleDays(), "Monday, Wednesday, Friday, ")
        assertNotEquals(mockedSerie2.scheduleDays(), "Monday, ")
    }

    @Test
    fun episode_premiere_year_format_isCorrect() {
        assertEquals(mockedSerie1.premiereYear(), "2020")

        assertNotEquals(mockedSerie1.premiereYear(), "2020-10-01")
        assertNotEquals(mockedSerie1.premiereYear(), "2020-10")
        assertNotEquals(mockedSerie1.premiereYear(), "2020-")
        assertNotEquals(mockedSerie1.premiereYear(), "")
        assertNotEquals(mockedSerie1.premiereYear(), null)

        assertEquals(mockedSerie2.premiereYear(), null)
    }
}