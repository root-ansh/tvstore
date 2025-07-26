package io.github.curioustools.tvstore.ui.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.components.FullScreenError
import io.github.curioustools.tvstore.ui.components.FullScreenLoader
import io.github.curioustools.tvstore.ui.components.MovieCard
import io.github.curioustools.tvstore.ui.components.PosterImage
import io.github.curioustools.tvstore.ui.screens.home.MovieDataViewModel
import io.github.curioustools.tvstore.ui.screens.home.MovieUiState
import io.github.curioustools.tvstore.ui.screens.home.MovieUiTriggers
import io.github.curioustools.tvstore.ui.screens.home.OwnerEvents
import io.github.curioustools.tvstore.ui.screens.home.rememberChildPadding
import io.github.curioustools.tvstore.ui.screens.player.focusOnInitialVisibility
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun CategoryResultsScreen(onBackPressed: () -> Unit, openMovieDetailsScreen: () -> Unit) {
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
    BackHandler(onBack = onBackPressed)
    CategoryResultsUi(state,openMovieDetailsScreen)
    FullScreenError(isVisible =showError.value!=null,showError.value)
    FullScreenLoader(showLoader.value)


}

@Composable
fun CategoryResultsUi(state: MovieUiState, openMovieDetailsScreen: () -> Unit) {
    val moviees = state.data?.data?.find { it.title.equals(SessionCache.selectedCategory,true)}?.videos.orEmpty()
    val childPadding = rememberChildPadding()
    val isFirstItemVisible = remember { mutableStateOf(false) }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
    ) {
        Text(
            text = SessionCache.selectedCategory,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.localUpdatedColors.white,
            modifier = Modifier.padding(
                vertical = childPadding.top.times(3.5f)
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            itemsIndexed(
                moviees,
                key = { _, movie -> movie.id }
            ) { index, movie ->
                MovieCard(
                    onClick = {
                        SessionCache.selectedMovie = movie
                        openMovieDetailsScreen.invoke()
                    },
                    modifier = Modifier
                        .aspectRatio(1 / 1.5f)
                        .padding(8.dp)
                        .then(
                            if (index == 0)
                                Modifier.focusOnInitialVisibility(isFirstItemVisible)
                            else Modifier
                        ),
                ) {
                    PosterImage(movie = movie, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

}