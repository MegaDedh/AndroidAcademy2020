package ru.asshands.softwire.androidacademy2020.domain

import ru.asshands.softwire.androidacademy2020.R
import ru.asshands.softwire.androidacademy2020.models.Actor
import ru.asshands.softwire.androidacademy2020.models.Movie

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        val loremIpsumStoryLine =
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."

        return listOf(
            Movie(
                id = 1_000_001,
                title = "Avengers: End Game",
                posterUrl = "https://image.jpg",
                posterResource = R.drawable.poster_1,
                genreTags = "Action, Adventure, Drama",
                rating = 4,
                reviews = 125,
                runningTime = 137,
                ageRating = "13+",
                favorite = false,
                actors = actor,
                storyline = loremIpsumStoryLine
            ),

            Movie(
                id = 1_000_002,
                title = "Tenet",
                posterUrl = "https://image.jpg",
                posterResource = R.drawable.poster_2,
                genreTags = "Action, Sci-Fi, Thriller",
                rating = 5,
                reviews = 98,
                runningTime = 97,
                ageRating = "16+",
                favorite = true,
                actors = actor,
                storyline = loremIpsumStoryLine
            ),

            Movie(
                id = 1_000_003,
                title = "Black Widow",
                posterUrl = "https://image.jpg",
                posterResource = R.drawable.poster_3,
                genreTags = "Action, Adventure, Sci-Fi",
                rating = 1,
                reviews = 38,
                runningTime = 102,
                ageRating = "13+",
                favorite = false,
                actors = actor,
                storyline = loremIpsumStoryLine
            ),

            Movie(
                id = 1_000_004,
                title = "Wonder Woman 1984",
                posterUrl = "https://image.jpg",
                posterResource = R.drawable.poster_4,
                genreTags = "Action, Adventure, Fantasy",
                rating = 2,
                reviews = 74,
                runningTime = 120,
                ageRating = "13+",
                favorite = false,
                actors = actor,
                storyline = loremIpsumStoryLine
            ),
        )


    }

    private val actor = listOf(
        Actor(
            id = 0,
            name = "Robert Downey Jr.",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_1,
        ),
        Actor(
            id = 0,
            name = "Chris Evans",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_2,
        ),
        Actor(
            id = 0,
            name = "Mark Ruffalo",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_3,
        ),
        Actor(
            id = 0,
            name = "Chris Hemsworth",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_4,
        ),
        Actor(
            id = 0,
            name = "Robert Downey Jr.",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_1,
        ),
        Actor(
            id = 0,
            name = "Chris Evans",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_2,
        ),
        Actor(
            id = 0,
            name = "Mark Ruffalo",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_3,
        ),
        Actor(
            id = 0,
            name = "Chris Hemsworth",
            surname = "",
            posterUrl = "https://image.jpg",
            posterResource = R.drawable.actor_4,
        )
    )
}