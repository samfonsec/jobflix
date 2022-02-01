package br.com.jobflix.serie

import br.com.jobflix.ImageMockProvider.mockedImage
import br.com.jobflix.data.model.Rating
import br.com.jobflix.data.model.Schedule
import br.com.jobflix.data.model.Serie

object SerieMockProvider {

    val mockedSerie1 by lazy {
        Serie(
            id = 1,
            name = "The Serie",
            genres = listOf("Drama", "Action", "Science-Fiction"),
            premiered = "2020-10-01",
            rating = mockedRating,
            image = mockedImage,
            summary = "Serie Summary",
            schedule = Schedule(
                time = "22:00",
                days = listOf("Monday", "Wednesday", "Friday")
            )
        )
    }

    val mockedSerie2 by lazy {
        Serie(
            id = 2,
            name = "The Other Serie",
            genres = listOf("Drama"),
            premiered = null,
            rating = mockedRating,
            image = mockedImage,
            summary = "Serie 2 Summary",
            schedule = Schedule(
                time = "",
                days = listOf("Monday")
            )
        )
    }

    private val mockedRating = Rating(
        average = 8.1
    )
}