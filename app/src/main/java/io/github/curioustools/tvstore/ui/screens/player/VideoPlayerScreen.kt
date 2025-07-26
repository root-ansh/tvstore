package io.github.curioustools.tvstore.ui.screens.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_TEXTURE_VIEW
import androidx.media3.ui.compose.modifiers.resizeWithContentScale
import io.github.curioustools.tvstore.api.MovieDTO
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.components.FullScreenError
import io.github.curioustools.tvstore.ui.components.FullScreenLoader

@Composable
fun VideoPlayerScreen(onBackPressed:()-> Unit){
    val showError = remember { mutableStateOf<FailureInfo?>(null) }
    val showLoader = remember { mutableStateOf(false) }

    val onErrorUpdate : (FailureInfo?) -> Unit = {showError.value = it}
    val onLoadingUpdate : (Boolean) -> Unit = {showLoader.value = it}


    FullScreenError(isVisible =showError.value!=null,showError.value)
    FullScreenLoader(showLoader.value)
    val movieDTO = SessionCache.selectedMovie
    VideoPlayerUI(movieDTO,onLoadingUpdate,onErrorUpdate,onBackPressed)
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoPlayerUI(
    movieDTO: MovieDTO,
    onLoadingUpdate: (Boolean) -> Unit,
    onErrorUpdate: (FailureInfo?) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = rememberPlayer(context)

    val videoPlayerState = rememberVideoPlayerState(
        hideSeconds = 4,
    )

    LaunchedEffect(exoPlayer, movieDTO) {
        exoPlayer.addMediaItem(movieDTO.videoUri.intoMediaItem())
        exoPlayer.prepare()
    }

    BackHandler(onBack = {
        exoPlayer.stop()
        onBackPressed.invoke()
    })

    val pulseState = rememberVideoPlayerPulseState()

    Box(
        Modifier
            .dPadEvents(
                exoPlayer,
                videoPlayerState,
                pulseState
            )
            .focusable()
    ) {
        PlayerSurface(
            player = exoPlayer,
            surfaceType = SURFACE_TYPE_TEXTURE_VIEW,
            modifier = Modifier.resizeWithContentScale(
                contentScale = ContentScale.Fit,
                sourceSizeDp = null
            )
        )

        val focusRequester = remember { FocusRequester() }
        VideoPlayerOverlay(
            modifier = Modifier.align(Alignment.BottomCenter),
            focusRequester = focusRequester,
            isPlaying = exoPlayer.isPlaying,
            isControlsVisible = videoPlayerState.isControlsVisible,
            centerButton = { VideoPlayerPulse(pulseState) },
            subtitles = { },
            showControls = videoPlayerState::showControls,
            controls = {
                VideoPlayerControls(
                    player = exoPlayer,
                    title = movieDTO.title,
                    releaseDate = movieDTO.releaseDate,
                    director = movieDTO.directors,
                    focusRequester = focusRequester,
                    onShowControls = { videoPlayerState.showControls(exoPlayer.isPlaying) },
                )
            }
        )
    }
}



