package br.com.jobflix.date

import br.com.jobflix.date.DateMockProvider.mockedDate2
import br.com.jobflix.date.DateMockProvider.mockedDate
import br.com.jobflix.date.DateMockProvider.mockedDate3
import br.com.jobflix.util.extensions.age
import br.com.jobflix.util.extensions.format
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateExtUnitTest {

    @Test
    fun date_to_string_format_isCorrect() {
        assertEquals(mockedDate.format(), "October 13, 1993")
        assertNotEquals(mockedDate.format(), "1993-10-13")
    }

    @Test
    fun date_to_age_isCorrect() {
        assertEquals(mockedDate.age(), 31)
        assertEquals(mockedDate2.age(), 32)
        assertEquals(mockedDate3.age(), 31)
    }
}