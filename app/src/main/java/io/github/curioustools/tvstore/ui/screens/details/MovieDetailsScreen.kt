package io.github.curioustools.tvstore.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.curioustools.tvstore.api.MovieDTO
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.ui.components.details.CastAndCrewList
import io.github.curioustools.tvstore.ui.components.details.DotSeparatedRow
import io.github.curioustools.tvstore.ui.components.details.MovieDescription
import io.github.curioustools.tvstore.ui.components.details.MovieLargeTitle
import io.github.curioustools.tvstore.ui.components.details.WatchTrailerButton
import io.github.curioustools.tvstore.ui.utils.rememberChildPadding
import io.github.curioustools.tvstore.ui.utils.BlurTransformation
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreen( goToMoviePlayer: () -> Unit, onBackPressed:()-> Unit){
    val movieDTO = SessionCache.selectedMovie
    val bg = MaterialTheme.colorScheme.surface

    BackHandler(onBack = onBackPressed)
    LazyColumn(
        contentPadding = PaddingValues(bottom = 135.dp),
        modifier = Modifier.background(bg).fillMaxSize().animateContentSize(),
    ) {
        item {
            MovieDetailsUI(
                movieDTO = movieDTO,
                goToMoviePlayer = {
                    SessionCache.selectedMovie = movieDTO
                    goToMoviePlayer.invoke()
                }
            )
        }

        item {
            val castAndCrew =
                movieDTO.directors.split(",").map { it.trim() } +
                        movieDTO.stars.split(",").map { it.trim() }
            CastAndCrewList(
                castAndCrew = castAndCrew
            )
        }

    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsUI(
    movieDTO: MovieDTO,
    goToMoviePlayer: () -> Unit
) {
    val childPadding = rememberChildPadding()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(432.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize().alpha(0.8f),
            model = ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(movieDTO.image_16_9)
                .transformations(listOf(BlurTransformation()))
                .build(),
            contentDescription = movieDTO.title,
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.fillMaxWidth(0.55f)) {
            Spacer(modifier = Modifier.height(108.dp))
            Column(
                modifier = Modifier.padding(start = childPadding.start)
            ) {
                MovieLargeTitle(movieTitle = movieDTO.title)

                Column(
                    modifier = Modifier.alpha(0.75f)
                ) {
                    MovieDescription(description = movieDTO.plot)
                    DotSeparatedRow(
                        modifier = Modifier.padding(top = 20.dp),
                        texts = listOf(
                            movieDTO.contentRating,
                            movieDTO.releaseDate,
                            movieDTO.year.toString(),
                            movieDTO.genres.split(",").joinToString(" , "),
                            movieDTO.rating.toString()
                        )
                    )

                }
                WatchTrailerButton(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                        }
                    },
                    goToMoviePlayer = goToMoviePlayer
                )
            }
        }
    }
}



