package io.github.curioustools.tvstore.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.MaterialTheme
import io.github.curioustools.tvstore.ui.screens.details.AppRoutes
import io.github.curioustools.tvstore.ui.screens.search.SearchScreen
import io.github.curioustools.tvstore.ui.screens.settings.SettingsScreen
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavHostController, snackBarHostState: SnackbarHostState){

    val colors = MaterialTheme.localUpdatedColors

    val coroutineScope = rememberCoroutineScope()

    val tabs: MutableList<AppRoutes> = mutableListOf(AppRoutes.Home(),  AppRoutes.Search,AppRoutes.Settings)

    val pagerState = rememberPagerState(0) { tabs.size }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = {/* PagerBarIndicatorComposable(pagerState, it)*/ },
            divider = { PagerDivider() },
            containerColor = colors.transparent,
            contentColor = colors.textInput
        ) {
            tabs.forEachIndexed { index, routeEnum ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                ) { TabText(routeEnum.getTabTitle(), isSelected) }
            }
        }
        HorizontalPager(
            userScrollEnabled = false,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pageNum ->
            val tab = tabs[pageNum]
            when(tab){
                is AppRoutes.Home -> ListingScreen(navController,snackBarHostState)
                AppRoutes.Search -> SearchScreen(navController,snackBarHostState)
                AppRoutes.Settings -> SettingsScreen(navController,snackBarHostState)
                else -> {}
            }
        }

    }



}


@Composable
fun PagerDivider(){
    Spacer(
        modifier = Modifier
            .background(MaterialTheme.localUpdatedColors.outlineV1)
            .fillMaxWidth()
            .height(1.dp)
    )
}

@Preview
@Composable
fun TabText(text: String = "Motor", isSelected:Boolean = false){
    val fontstyle = MaterialTheme.typography
    val colors = MaterialTheme.localUpdatedColors

    Text(
        text = text,
        style = if (isSelected) fontstyle.titleSmall else fontstyle.labelLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = if (isSelected) colors.ctaSecondaryEnabledText else colors.textDarkV2,
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        textAlign = TextAlign.Center
    )
}




@Composable
fun ListingScreen(navController: NavHostController, snackBarHostState: SnackbarHostState) {
    Text("Dashboard Screen")


}


