package io.github.curioustools.tvstore.ui.screens.details

import androidx.annotation.Keep
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.github.curioustools.tvstore.ui.screens.home.HomeScreen
import io.github.curioustools.tvstore.ui.screens.search.SearchScreen
import io.github.curioustools.tvstore.ui.screens.settings.SettingsScreen
import io.github.curioustools.tvstore.ui.screens.player.VideoPlayerScreen
import kotlinx.serialization.Serializable

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        route = "root",
        startDestination = AppRoutes.Home().route,
        enterTransition = { slideInWithFade() },
        exitTransition = { slideOutWithFade() },
        popEnterTransition = { popSlideInWithFade() },
        popExitTransition = { popSlideOutWithFade() }
    )
    {
        navigation(route = AppRoutes.Home().route , startDestination ="home_screen" ){
            composable("home_screen") { HomeScreen(navController, snackBarHostState) }
        }
        composable<AppRoutes.MovieDetails> { MovieDetailsScreen(navController, snackBarHostState) }
        composable<AppRoutes.VideoPlayer> { VideoPlayerScreen(navController, snackBarHostState) }
        composable<AppRoutes.Settings> { SettingsScreen(navController, snackBarHostState) }
        composable<AppRoutes.Search> { SearchScreen(navController, snackBarHostState) }

    }
}


fun slideInWithFade(duration: Int = 500) = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(duration, easing = FastOutSlowInEasing)
) + fadeIn(tween(duration))

fun slideOutWithFade(duration: Int = 500) = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(duration, easing = FastOutSlowInEasing)
) + fadeOut(tween(duration))

fun popSlideInWithFade(duration: Int = 500) = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(duration, easing = FastOutSlowInEasing)
) + fadeIn(tween(duration))

fun popSlideOutWithFade(duration: Int = 500) = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(duration, easing = FastOutSlowInEasing)
) + fadeOut(tween(duration))


@Keep
sealed interface AppRoutes {
    @Serializable data class Home(val route: String = "home") : AppRoutes
    @Serializable data object Settings : AppRoutes
    @Serializable data object Search : AppRoutes
    @Serializable data object MovieDetails : AppRoutes
    @Serializable data class VideoPlayer(val url: String) : AppRoutes

    fun getTabTitle(): String{
        return when(this){
            is Home -> "Home"
            MovieDetails -> ""
            Search -> "Search"
            Settings -> "Settings"
            is VideoPlayer -> ""
        }
    }
}

