package io.github.curioustools.tvstore.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.curioustools.tvstore.api.MovieDTO

@Composable
fun PosterImage(
    movie: MovieDTO,
    isVertical: Boolean =true,
    modifier: Modifier = Modifier.Companion,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(if (isVertical) movie.image_2_3 else movie.image_16_9)
            .build(),
        contentDescription = movie.title,
        contentScale = ContentScale.Companion.Crop
    )
}