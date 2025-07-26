package io.github.curioustools.tvstore.ui.screens.categories

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.components.FullScreenError
import io.github.curioustools.tvstore.ui.components.FullScreenLoader
import io.github.curioustools.tvstore.ui.components.MovieCard
import io.github.curioustools.tvstore.ui.screens.home.ListingScreenUI
import io.github.curioustools.tvstore.ui.screens.home.MovieDataViewModel
import io.github.curioustools.tvstore.ui.screens.home.MovieUiState
import io.github.curioustools.tvstore.ui.screens.home.MovieUiTriggers
import io.github.curioustools.tvstore.ui.screens.home.OwnerEvents
import io.github.curioustools.tvstore.ui.screens.home.rememberChildPadding
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun CategoriesScreen(
    onCategoryClick: (categoryId: String) -> Unit,
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

    CategoryGridUi(state,onCategoryClick,onScroll)
    FullScreenError(isVisible =showError.value!=null,showError.value)
    FullScreenLoader(showLoader.value)

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CategoryGridUi(
    state: MovieUiState,
    onCategoryClick: (categoryId: String) -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
) {
    val movieCategories = state.data?.data.orEmpty().map { it.title }
    val childPadding = rememberChildPadding()
    val lazyGridState = rememberLazyGridState()
    val shouldShowTopBar by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex == 0 &&
                    lazyGridState.firstVisibleItemScrollOffset < 100
        }
    }
    LaunchedEffect(shouldShowTopBar) {
        onScroll(shouldShowTopBar)
    }

    AnimatedContent(
        targetState = movieCategories,
        modifier = Modifier
            .padding(horizontal = childPadding.start)
            .padding(top = childPadding.top),
        label = "",
    ) { it ->
        LazyVerticalGrid(
            state = lazyGridState,
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(4),
        ) {
            itemsIndexed(it) { index, movieCategory ->
                var isFocused by remember { mutableStateOf(false) }
                MovieCard(
                    onClick = {
                        SessionCache.selectedCategory = movieCategory
                        onCategoryClick(movieCategory)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(16 / 9f)
                        .onFocusChanged {
                            isFocused = it.isFocused || it.hasFocus
                        }
                        .focusProperties {
                            if (index % 4 == 0) {
                                left = FocusRequester.Cancel
                            }
                        }
                ) {
                    val itemAlpha by animateFloatAsState(
                        targetValue = if (isFocused) .6f else 0.2f,
                        label = ""
                    )
                    val textColor = if (isFocused) Color.Black else Color.White

                    Box(contentAlignment = Alignment.Center) {
                        Box(modifier = Modifier.alpha(itemAlpha)) {
                            GradientBg()
                        }
                        Text(
                            text = movieCategory.capitalize(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = textColor,
                            )
                        )
                    }
                }
            }
        }
    }
}







@Composable
fun GradientBg() {
    Box(
        modifier = Modifier
            .background(Brush.linearGradient(MaterialTheme.localUpdatedColors.pairs.random().toList()))
            .fillMaxWidth()
            .height(200.dp)
    )
}