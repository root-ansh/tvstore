package io.github.curioustools.tvstore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.curioustools.tvstore.ui.screens.details.RootNavigationGraph
import io.github.curioustools.tvstore.ui.utils.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val movieDataViewModel by viewModels<MovieDataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            AppTheme {
                Scaffold(
                    snackbarHost = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            SnackbarHost(hostState = snackBarHostState, modifier = Modifier.padding(16.dp).fillMaxWidth().align(Alignment.BottomCenter))
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) {  paddingValues ->
                    RootNavigationGraph(
                        navController = navController,
                        snackBarHostState = snackBarHostState
                    )
                }
            }

        }
    }


}