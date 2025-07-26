package io.github.curioustools.tvstore.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.curioustools.tvstore.ui.screens.details.MovieDetailsScreen
import io.github.curioustools.tvstore.ui.screens.home.DashboardScreen
import io.github.curioustools.tvstore.ui.screens.player.VideoPlayerScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    onBackPressedSystem : () -> Unit
) {
    var isComingBackFromDifferentScreen by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Dashboard.route,
        enterTransition = { slideInWithFade() },
        exitTransition = { slideOutWithFade() },
        popEnterTransition = { popSlideInWithFade() },
        popExitTransition = { popSlideOutWithFade() },
        builder = {

               composable(route = AppRoutes.Dashboard.route){
                   DashboardScreen(
                       onBackPressed = onBackPressedSystem,
                       isComingBackFromDifferentScreen = isComingBackFromDifferentScreen,
                       openVideoPlayer = {navController.navigate(AppRoutes.VideoPlayer.route)},
                       openMovieDetailsScreen = {navController.navigate(AppRoutes.MovieDetails.route)},
                       resetIsComingBackFromDifferentScreen = {isComingBackFromDifferentScreen = false},
                   )
               }
                composable(route = AppRoutes.MovieDetails.route) { MovieDetailsScreen(navController, snackBarHostState) }
                composable(route = AppRoutes.VideoPlayer.route){VideoPlayerScreen(navController,snackBarHostState)}

        }
    )

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



enum class AppRoutes(val route: String) {
    Dashboard("dashboard"),
    Sub_Home("home"),
    Sub_Categories("categories"),
    Sub_Search("search"),
    MovieDetails("details"),
    VideoPlayer("player");

    companion object{
        fun find(s: String): AppRoutes{
            return entries.find { it.route.equals(s,true) }?: AppRoutes.Sub_Home
        }
    }

}


