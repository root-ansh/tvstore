package io.github.curioustools.tvstore.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.curioustools.tvstore.api.MovieDataUseCase
import io.github.curioustools.tvstore.api.MovieModel
import io.github.curioustools.tvstore.api.SessionCache
import io.github.curioustools.tvstore.api.SharedPrefs
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.base.extractFailureInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDataViewModel @Inject constructor(
    private val movieDataUseCase: MovieDataUseCase,
    private val prefs: SharedPrefs
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val _ownerEventFlow = MutableSharedFlow<OwnerEvents>()
    val ownerEventFlow: SharedFlow<OwnerEvents> = _ownerEventFlow.asSharedFlow()

    fun onUiTrigger(trigger: MovieUiTriggers) {
        when (trigger) {
            is MovieUiTriggers.LoadMovies -> requestMoviesAndEmit()
        }
    }

    private fun requestMoviesAndEmit() {
        viewModelScope.launch {
            delay(100)
            _ownerEventFlow.emit(OwnerEvents.ShowLoader())
            try {
                val result: MovieModel
                if(!SessionCache.hasRequestedFromApiOnce){
                    SessionCache.hasRequestedFromApiOnce = true
                    result = movieDataUseCase.execute("")
                    prefs.listingCache = result
                }else{
                    val cachedResp = prefs.listingCache
                    result = if(cachedResp!=null && cachedResp.data.isNotEmpty()) cachedResp
                    else  movieDataUseCase.execute("")
                }
                val resultWithFavourites: MovieModel = getResultWithFavourites(result)
                _uiState.update {  it.copy(data = resultWithFavourites)}
                _ownerEventFlow.emit(OwnerEvents.DoNothing)
            } catch (t: Throwable) {
                _ownerEventFlow.emit(OwnerEvents.ShowError(t.extractFailureInfo()))
            }
        }
    }

    private fun getResultWithFavourites(result: MovieModel): MovieModel {
        val cacheIds = prefs.favouriteIds.split(",")
        val favouriteVideos = result.data.map { it.videos }.flatten().filter { it.id in cacheIds }
        if(favouriteVideos.isEmpty()) return  result
        val newCategories = result.data.toMutableList()
        newCategories.add(0, MovieModel.Category(title = "Your Favourites", videos = favouriteVideos))
        return result.copy(newCategories)
    }
}

sealed interface MovieUiTriggers {
    object LoadMovies : MovieUiTriggers
}
sealed interface OwnerEvents{
    data class ShowLoader(val msg: String ="") : OwnerEvents
    data class ShowError(val info : FailureInfo): OwnerEvents
    data object DoNothing : OwnerEvents
    data class NavigateToComposable(val route: String):OwnerEvents
}
data class MovieUiState(
    val data: MovieModel? = null,
)