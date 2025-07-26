package io.github.curioustools.tvstore.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.components.FullScreenError
import io.github.curioustools.tvstore.ui.components.FullScreenLoader
import io.github.curioustools.tvstore.ui.components.MoviesRow

@Composable
fun ListingScreen(
    onMovieClick: () -> Unit,
    goToVideoPlayer: () -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    isTopBarVisible: Boolean,
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
    ListingScreenUI(state, onMovieClick,onScroll,isTopBarVisible)
    FullScreenError(isVisible =showError.value!=null,showError.value)
    FullScreenLoader(showLoader.value)


}

@Preview
@Composable
fun ListingScreenUI(
    state: MovieUiState = MovieUiState(),
    goToDetails: () -> Unit = {},
    onScroll: (Boolean) -> Unit = {},
    isTopBarVisible: Boolean = false) {
    val lazyListState = rememberLazyListState()
    val childPadding = rememberChildPadding()
    var immersiveListHasFocus by remember { mutableStateOf(false) }

    val shouldShowTopBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset < 300
        }
    }
    LaunchedEffect(shouldShowTopBar) {
        onScroll(shouldShowTopBar)
    }
    LaunchedEffect(isTopBarVisible) {
        if (isTopBarVisible) lazyListState.animateScrollToItem(0)
    }

    val data = state.data?.data.orEmpty()

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(bottom = 108.dp)

    ) {
        itemsIndexed(data){i,category ->
            MoviesRow(
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                movieList = category,
                title = category.title,
                onMovieSelected = {
                    SessionCache.selectedMovie = it
                    goToDetails.invoke()
                }
            )
        }
    }
}


