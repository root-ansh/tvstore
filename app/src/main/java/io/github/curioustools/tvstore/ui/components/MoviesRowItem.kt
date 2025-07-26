package io.github.curioustools.tvstore.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import io.github.curioustools.tvstore.api.MovieDTO

@OptIn(ExperimentalComposeUiApi::class)
@Composable
 fun MoviesRowItem(
    index: Int,
    movie: MovieDTO,
    onMovieSelected: (MovieDTO) -> Unit,
    showItemTitle: Boolean,
    showIndexOverImage: Boolean,
    isVertical: Boolean,
    modifier: Modifier = Modifier.Companion,
    onMovieFocused: (MovieDTO) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }

    MovieCard(
        onClick = { onMovieSelected(movie) },
        title = {
            MoviesRowItemText(
                showItemTitle = showItemTitle,
                isItemFocused = isFocused,
                movie = movie
            )
        },
        modifier = Modifier.Companion
            .onFocusChanged {
                isFocused = it.isFocused
                if (it.isFocused) {
                    onMovieFocused(movie)
                }
            }
            .focusProperties {
                left = if (index == 0) {
                    FocusRequester.Companion.Cancel
                } else {
                    FocusRequester.Companion.Default
                }
            }
            .then(modifier)
    ) {
        MoviesRowItemImage(
            modifier = Modifier.Companion.aspectRatio(if(isVertical) 10.5f / 16f else 16f / 9f),
            showIndexOverImage = showIndexOverImage,
            movie = movie,
            index = index,
            isVertical = isVertical
        )
    }
}