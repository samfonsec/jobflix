package br.com.jobflix.episode

import br.com.jobflix.episode.EpisodeMockProvider.mockEpisodeS10E23
import br.com.jobflix.episode.EpisodeMockProvider.mockEpisodeS1E1
import br.com.jobflix.episode.EpisodeMockProvider.mockEpisodeS2E12
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EpisodeUnitTest {

    @Test
    fun episode_number_format_isCorrect() {
        assertEquals(mockEpisodeS1E1().formattedNumber(), "S01 - E01")
        assertEquals(mockEpisodeS10E23().formattedNumber(), "S10 - E23")
        assertEquals(mockEpisodeS2E12().formattedNumber(), "S02 - E12")

        assertNotEquals(mockEpisodeS1E1().formattedNumber(), "S1 - E1")
    }

    @Test
    fun episode_runtime_format_isCorrect() {
        assertEquals(mockEpisodeS1E1().formattedRuntime(), "60m")
        assertEquals(mockEpisodeS10E23().formattedRuntime(), "120m")
        assertEquals(mockEpisodeS2E12().formattedRuntime(), "05m")

        assertNotEquals(mockEpisodeS2E12().formattedRuntime(), "5m")
    }
}