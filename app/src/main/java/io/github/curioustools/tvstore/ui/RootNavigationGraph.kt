package io.github.curioustools.tvstore.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.curioustools.tvstore.ui.screens.categories.CategoriesScreen
import io.github.curioustools.tvstore.ui.screens.categories.CategoryResultsScreen
import io.github.curioustools.tvstore.ui.screens.details.MovieDetailsScreen
import io.github.curioustools.tvstore.ui.screens.home.DashboardScreen
import io.github.curioustools.tvstore.ui.screens.home.ListingScreen
import io.github.curioustools.tvstore.ui.screens.player.VideoPlayerScreen
import io.github.curioustools.tvstore.ui.screens.search.SearchScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
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
                       openCategoryResults = {navController.navigate(AppRoutes.CategoryResults.route)},
                       resetIsComingBackFromDifferentScreen = {isComingBackFromDifferentScreen = false},
                   )
               }
            composable(route = AppRoutes.MovieDetails.route) {
                MovieDetailsScreen(
                    goToMoviePlayer = { navController.navigate(AppRoutes.VideoPlayer.route) },
                    onBackPressed = { navController.popBackStack() }
                )
            }
            composable(route = AppRoutes.VideoPlayer.route) {
                VideoPlayerScreen(
                    onBackPressed = {navController.popBackStack()}
                )
            }
            composable(route = AppRoutes.CategoryResults.route) {
                CategoryResultsScreen(
                    onBackPressed = { navController.popBackStack() },
                    openMovieDetailsScreen = { navController.navigate(AppRoutes.MovieDetails.route) },
                )
            }
        }
    )

}

@Composable
fun DashboardSubGraph(
    openMovieDetailsScreen: () -> Unit,
    openVideoPlayer: () -> Unit,
    openCategoryResults: () -> Unit,
    updateTopBarVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isTopBarVisible: Boolean = true,
) =
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppRoutes.SubGraphHome.route,
    ) {

        composable(AppRoutes.SubGraphHome.route) {
            ListingScreen(
                onMovieClick = openMovieDetailsScreen,
                goToVideoPlayer = openVideoPlayer,
                onScroll = updateTopBarVisibility,
                isTopBarVisible = isTopBarVisible
            )
        }
        composable(AppRoutes.SubGraphCategories.route) {
            CategoriesScreen(
                onCategoryClick = { openCategoryResults.invoke() },
                onScroll = updateTopBarVisibility
            )
        }
        composable(AppRoutes.SubGraphSearch.route) {
            SearchScreen(
                onMovieClick = openMovieDetailsScreen,
                onScroll = updateTopBarVisibility
            )
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



enum class AppRoutes(val route: String) {
    Dashboard("dashboard"),
    SubGraphHome("home"),
    SubGraphCategories("categories"),
    SubGraphSearch("search"),
    CategoryResults("category_results"),
    MovieDetails("details"),
    VideoPlayer("player");

    companion object{
        fun findByRoute(s: String): AppRoutes{
            return entries.find { it.route.equals(s,true) }?: SubGraphHome
        }
    }

}


