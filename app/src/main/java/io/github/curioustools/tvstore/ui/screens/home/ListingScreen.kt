package io.github.curioustools.tvstore.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun ListingScreen(
    onMovieClick: () -> Unit,
    goToVideoPlayer: () -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    isTopBarVisible: Boolean,
) {
    Column(Modifier.fillMaxSize()) {
        repeat(10){idx ->
            Text("Text$idx", color = MaterialTheme.localUpdatedColors.white)
            Spacer(Modifier.size(42.dp))
        }
    }

}

@Composable
fun SearchScreen(
    onMovieClick: () -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
) {
    Text("SearchScreen Screen")

}

@Composable
fun CategoriesScreen(
    onCategoryClick: (categoryId: String) -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
) {
    Text("Catgories Screen")

}
