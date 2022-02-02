package br.com.jobflix.serie

import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.viewModel.details.SerieDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SerieDetailsViewModelUnitTest {

    @MockK
    private lateinit var seriesRepository: SeriesRepository

    private lateinit var viewModel: SerieDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SerieDetailsViewModel(seriesRepository)
    }

    @Test
    fun get_selected_season_number_isCorrect() {
        with(viewModel) {
            assertEquals(getSeasonNumber("Season 01"), 1)
            assertEquals(getSeasonNumber("Season 10"), 10)
            assertEquals(getSeasonNumber("Season 2005"), 2005)

            assertNotEquals(getSeasonNumber("Season 01"), null)
        }
    }
}