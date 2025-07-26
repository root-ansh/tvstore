package io.github.curioustools.tvstore.ui.screens.search

import android.view.KeyEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.api.MovieDTO
import io.github.curioustools.tvstore.api.MovieModel
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.components.FullScreenError
import io.github.curioustools.tvstore.ui.components.FullScreenLoader
import io.github.curioustools.tvstore.ui.components.MoviesRow
import io.github.curioustools.tvstore.ui.screens.home.MovieDataViewModel
import io.github.curioustools.tvstore.ui.screens.home.MovieUiState
import io.github.curioustools.tvstore.ui.screens.home.MovieUiTriggers
import io.github.curioustools.tvstore.ui.screens.home.OwnerEvents
import io.github.curioustools.tvstore.ui.screens.home.rememberChildPadding

@Composable
fun SearchScreen(
    onMovieClick: () -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
) {

    val viewModel = hiltViewModel<MovieDataViewModel>()
    val lifecycle = LocalLifecycleOwner.current
    val ctx = LocalContext.current
    val showError = remember { mutableStateOf<FailureInfo?>(null) }
    val showLoader = remember { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsStateWithLifecycle(lifecycle.lifecycle, Lifecycle.State.STARTED)

    LaunchedEffect(Unit) {
        viewModel.onUiTrigger(MovieUiTriggers.LoadMovies)
    }
    LaunchedEffect(Unit) {
        viewModel.ownerEventFlow.flowWithLifecycle(lifecycle.lifecycle,Lifecycle.State.STARTED).collect {event ->
            showError.value = null
            showLoader.value = false
            when(event){
                OwnerEvents.DoNothing -> {}
                is OwnerEvents.NavigateToComposable -> {}
                is OwnerEvents.ShowError -> {showError.value = event.info}
                is OwnerEvents.ShowLoader -> {showLoader.value = true}
            }
        }
    }

    FullScreenError(isVisible =showError.value!=null,showError.value)
    FullScreenLoader(showLoader.value)

    SearchScreenUI(state,onMovieClick)

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreenUI(state: MovieUiState, onMovieClick: () -> Unit) {
    val lazyColumnState: LazyListState = rememberLazyListState()
    val allVids = state.data?.data?.map { it.videos }?.flatten().orEmpty()
    var searchResults by remember { mutableStateOf(listOf<MovieDTO>()) }
    val onSearch:(String)-> Unit = { query ->
        val filtered = allVids.filter {
            it.title.contains(query,true)
                    || it.plot.contains(query,true)
                    || it.genres.contains(query,true)
                    || it.stars.contains(query,true)
                    || it.directors.contains(query,true)
        }
        searchResults = filtered.toSet().toList()
    }

    val childPadding = rememberChildPadding()
    var searchQuery: String by remember { mutableStateOf("") }
    val tfFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val tfInteractionSource = remember { MutableInteractionSource() }

    val isTfFocused by tfInteractionSource.collectIsFocusedAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        state = lazyColumnState
    ) {
        item {
            Surface(
                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(12.dp)),
                scale = ClickableSurfaceDefaults.scale(focusedScale = 1f),
                colors = ClickableSurfaceDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    pressedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    focusedContentColor = MaterialTheme.colorScheme.onSurface,
                    pressedContentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = ClickableSurfaceDefaults.border(
                    focusedBorder = Border(
                        border = BorderStroke(
                            width = if (isTfFocused) 2.dp else 1.dp,
                            color = animateColorAsState(
                                targetValue = if (isTfFocused) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.border,
                                label = ""
                            ).value
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                ),
                tonalElevation = 2.dp,
                modifier = Modifier
                    .padding(horizontal = childPadding.start)
                    .padding(top = 8.dp),
                onClick = { tfFocusRequester.requestFocus() }
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { updatedQuery -> searchQuery = updatedQuery },
                    decorationBox = {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .padding(start = 20.dp),
                        ) {
                            it()
                            if (searchQuery.isEmpty()) {
                                Text(
                                    modifier = Modifier.graphicsLayer { alpha = 0.6f },
                                    text = "Search for movies, stars, genres...",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                        .focusRequester(tfFocusRequester)
                        .onKeyEvent {
                            if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
                                when (it.nativeKeyEvent.keyCode) {
                                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }

                                    KeyEvent.KEYCODE_DPAD_UP -> {
                                        focusManager.moveFocus(FocusDirection.Up)
                                    }

                                    KeyEvent.KEYCODE_BACK -> {
                                        focusManager.moveFocus(FocusDirection.Exit)
                                    }
                                }
                            }
                            true
                        },
                    cursorBrush = Brush.verticalGradient(
                        colors = listOf(
                            LocalContentColor.current,
                            LocalContentColor.current,
                        )
                    ),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch.invoke(searchQuery)
                        }
                    ),
                    maxLines = 1,
                    interactionSource = tfInteractionSource,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

        item {
            MoviesRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = childPadding.top * 2),
                movieList = MovieModel.Category(videos = searchResults)
            ) { selectedMovie ->
                SessionCache.selectedMovie = selectedMovie
                onMovieClick()
            }
        }
    }
}
