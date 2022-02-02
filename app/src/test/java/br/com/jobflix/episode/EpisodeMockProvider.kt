package br.com.jobflix.episode

import br.com.jobflix.ImageMockProvider.mockedImage
import br.com.jobflix.data.model.Episode

object EpisodeMockProvider {

    fun mockEpisodeS1E1() = Episode(
        id = 1,
        name = "Pilot",
        season = 1,
        number = 1,
        runtime = 60,
        image = mockedImage,
        summary = "Episode Summary"
    )

    fun mockEpisodeS10E23() = Episode(
        id = 2,
        name = "Pilot",
        season = 10,
        number = 23,
        runtime = 120,
        image = mockedImage,
        summary = "Episode Summary"
    )

    fun mockEpisodeS2E12() = Episode(
        id = 3,
        name = "Pilot",
        season = 2,
        number = 12,
        runtime = 5,
        image = mockedImage,
        summary = "Episode Summary"
    )

    fun mockEpisodesList() = listOf(mockEpisodeS1E1(), mockEpisodeS10E23(), mockEpisodeS10E23())

    fun mockEpisodesGroupedBySeasonList() = mockEpisodesList().groupBy { it.season }
}