package io.github.curioustools.tvstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.api.MovieDTO
import io.github.curioustools.tvstore.api.MovieModel
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.components.details.CastAndCrewItem
import io.github.curioustools.tvstore.ui.components.details.CastAndCrewList
import io.github.curioustools.tvstore.ui.components.details.DotSeparatedRow
import io.github.curioustools.tvstore.ui.components.details.MovieDescription
import io.github.curioustools.tvstore.ui.components.details.MovieLargeTitle
import io.github.curioustools.tvstore.ui.components.details.WatchTrailerButton

// Sample data for previews
private val sampleMovie = MovieDTO(
    id = "preview_movie_1",
    title = "The Great Adventure",
    fullTitle = "The Great Adventure (2024)",
    year = 2024,
    releaseDate = "15 Mar 2024",
    image_16_9 = "https://storage.googleapis.com/androiddevelopers/samples/media/posters/16_9-400/on-the-bridge.jpg",
    image_2_3 = "https://storage.googleapis.com/androiddevelopers/samples/media/posters/2_3-300/on-the-bridge.jpg",
    runtimeMins = 145,
    runtimeStr = "145 mins",
    plot = "An epic journey through uncharted territories where heroes face incredible challenges and discover the true meaning of courage.",
    contentRating = "PG-13",
    rating = 8.7,
    ratingCount = 125000,
    metaCriticRating = 85,
    genres = "Action,Adventure,Drama",
    directors = "Christopher Nolan,James Cameron",
    stars = "Tom Hardy,Emma Stone,Leonardo DiCaprio",
    videoUri = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4",
    subtitleUri = "https://thepaciellogroup.github.io/AT-browser-tests/video/subtitles-en.vtt"
)

private val sampleMovie2 = MovieDTO(
    id = "preview_movie_2",
    title = "Mystery Island",
    fullTitle = "Mystery Island (2023)",
    year = 2023,
    releaseDate = "22 Nov 2023",
    image_16_9 = "https://storage.googleapis.com/androiddevelopers/samples/media/posters/16_9-400/inventor.jpg",
    image_2_3 = "https://storage.googleapis.com/androiddevelopers/samples/media/posters/2_3-300/inventor.jpg",
    runtimeMins = 132,
    runtimeStr = "132 mins",
    plot = "A thrilling mystery unfolds on a remote island where nothing is as it seems.",
    contentRating = "R",
    rating = 7.9,
    ratingCount = 89000,
    metaCriticRating = 78,
    genres = "Mystery,Thriller",
    directors = "David Fincher",
    stars = "Jake Gyllenhaal,Rooney Mara",
    videoUri = "https://sample-videos.com/zip/10/mp4/SampleVideo_1280x720_1mb.mp4",
    subtitleUri = "https://thepaciellogroup.github.io/AT-browser-tests/video/subtitles-en.vtt"
)

private val sampleCategory = MovieModel.Category(
    id = "action",
    title = "Action Movies",
    subtitle = "High-octane action films",
    videos = listOf(sampleMovie, sampleMovie2)
)

private val sampleCastAndCrew = listOf(
    "Tom Hardy",
    "Emma Stone", 
    "Leonardo DiCaprio",
    "Christopher Nolan",
    "James Cameron"
)

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MovieCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MovieCard(
            onClick = {},
            title = {
                Text(
                    text = sampleMovie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            image = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray)
                ) {
                    Text(
                        text = "Movie Poster",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CastAndCrewItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        CastAndCrewItem(
            castMember = "Tom Hardy"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CastAndCrewListPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        CastAndCrewList(
            castAndCrew = sampleCastAndCrew
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MoviesRowPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MoviesRow(
            movieList = sampleCategory,
            title = "Action Movies"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MoviesRowItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MoviesRowItem(
            index = 0,
            movie = sampleMovie,
            onMovieSelected = {},
            showItemTitle = true,
            showIndexOverImage = false,
            isVertical = true
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MoviesRowItemImagePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MoviesRowItemImage(
            movie = sampleMovie,
            showIndexOverImage = true,
            index = 1,
            isVertical = true,
            modifier = Modifier.width(200.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MoviesRowItemTextPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MoviesRowItemText(
            showItemTitle = true,
            isItemFocused = true,
            movie = sampleMovie
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PosterImagePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        PosterImage(
            movie = sampleMovie,
            isVertical = true,
            modifier = Modifier.width(200.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun GradientBgPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        GradientBg()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MovieLargeTitlePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MovieLargeTitle(
            movieTitle = sampleMovie.title
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MovieDescriptionPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MovieDescription(
            description = sampleMovie.plot
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun DotSeparatedRowPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        DotSeparatedRow(
            texts = listOf(
                sampleMovie.contentRating,
                sampleMovie.releaseDate,
                sampleMovie.year.toString(),
                sampleMovie.genres.split(",").firstOrNull() ?: "",
                sampleMovie.rating.toString()
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun WatchTrailerButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        WatchTrailerButton(
            goToMoviePlayer = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun FullScreenLoaderPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        FullScreenLoader(
            isVisible = true,
            message = "Loading movies..."
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun FullScreenErrorPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        FullScreenError(
            isVisible = true,
            info = FailureInfo(
                error = Throwable("Network error"),
                code = 500,
                message = "Unable to load movies. Please check your connection."
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun GradientCircularProgressIndicatorPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        GradientCircularProgressIndicator(
            size = 72.dp,
            gradientColors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
            ),
            strokeWidth = 16.dp
        )
    }
}

// Combined preview showing multiple components together
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CombinedComponentsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        MovieLargeTitle(movieTitle = sampleMovie.title)
        Spacer(modifier = Modifier.height(16.dp))
        MovieDescription(description = sampleMovie.plot)
        Spacer(modifier = Modifier.height(16.dp))
        DotSeparatedRow(
            texts = listOf(
                sampleMovie.contentRating,
                sampleMovie.releaseDate,
                sampleMovie.year.toString()
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        WatchTrailerButton(goToMoviePlayer = {})
        Spacer(modifier = Modifier.height(32.dp))
        CastAndCrewList(castAndCrew = sampleCastAndCrew.take(3))
    }
}

