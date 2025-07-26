package io.github.curioustools.tvstore.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.tv.material3.Border
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ClassicCard
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.curioustools.tvstore.api.MovieDTO
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.ui.screens.home.rememberChildPadding
import io.github.curioustools.tvstore.ui.utils.BlurTransformation
import io.github.curioustools.tvstore.ui.utils.UpdatedColorXml
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
            MainDetails(
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
fun MainDetails(
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

@Composable
fun DotSeparatedRow(
    modifier: Modifier = Modifier,
    texts: List<String>
) {
    val textC = MaterialTheme.localUpdatedColors.white
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        texts.forEachIndexed { index, text ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = textC
            )
            if (index != texts.lastIndex) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(textC)
                        .size(4.dp)
                )
            }
        }
    }
}



@Composable
private fun WatchTrailerButton(
    modifier: Modifier = Modifier,
    goToMoviePlayer: () -> Unit
) {
    Button(
        colors = ButtonDefaults.colors(focusedContainerColor = MaterialTheme.localUpdatedColors.starYellow),
        onClick = goToMoviePlayer,
        modifier = modifier.padding(top = 24.dp),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = ButtonDefaults.shape(shape = RoundedCornerShape(4.dp))
    ) {
        Icon(
            imageVector = Icons.Outlined.PlayArrow,
            contentDescription = null
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = "Play",
            style = MaterialTheme.typography.titleSmall
        )
    }
}



@Composable
private fun MovieDescription(description: String) {
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white
    Text(
        text = description,
        style = MaterialTheme.typography.titleSmall.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(top = 8.dp),
        color = text
    )
}

@Composable
private fun MovieLargeTitle(movieTitle: String) {
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white
    Text(
        text = movieTitle,
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        maxLines = 1,
        color = text
    )
}


@OptIn(ExperimentalComposeUiApi::class) @Composable
fun CastAndCrewList(castAndCrew: List<String>) {
    val childPadding = rememberChildPadding()
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white

    Column(
        modifier = Modifier.padding(top = childPadding.top).background(bg),
    ) {
        Text(
            text = "Cast and Crew",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(start = childPadding.start),
            color = text
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 16.dp)
                .focusRestorer(),
            contentPadding = PaddingValues(start = childPadding.start)
        ) {
            items(castAndCrew, key = { it }) {
                CastAndCrewItem(it, modifier = Modifier.width(144.dp))
            }
        }
    }
}

@Composable
private fun CastAndCrewItem(
    castMember: String,
    modifier: Modifier = Modifier,
) {
    ClassicCard(
        modifier = modifier
            .padding(end = 20.dp, bottom = 16.dp)
            .aspectRatio(1 / 1.8f),
        shape = CardDefaults.shape(shape = RoundedCornerShape(12.dp)),
        scale = CardDefaults.scale(focusedScale = 1f),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(12.dp)
            )
        ),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 12.dp),
                text = castMember,
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis
            )
        },
        subtitle = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 12.dp),
                text = "cast",
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis
            )
        },
        image = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.725f)
                    .background(UpdatedColorXml().coolColors.random())
            )
        },
        onClick = {}
    )
}