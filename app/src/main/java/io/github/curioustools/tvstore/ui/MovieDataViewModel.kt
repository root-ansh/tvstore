package io.github.curioustools.tvstore.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.BaseTransientBottomBar
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.curioustools.tvstore.api.MovieDataUseCase
import io.github.curioustools.tvstore.api.MovieModel
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.base.extractFailureInfo
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
    private val movieDataUseCase: MovieDataUseCase
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
            _ownerEventFlow.emit(OwnerEvents.ShowLoader())
            try {
                val result = movieDataUseCase.execute("")
                _uiState.update {  it.copy(data = result)}
                _ownerEventFlow.emit(OwnerEvents.DoNothing)
            } catch (t: Throwable) {
                _ownerEventFlow.emit(OwnerEvents.ShowError(t.extractFailureInfo()))
            }
        }
    }
}

sealed interface MovieUiTriggers {
    object LoadMovies : MovieUiTriggers
}
sealed interface OwnerEvents{
    data class ShowLoader(val msg: String ="") : OwnerEvents
    data class ShowToast(val resId: Int, val duration: Int = Toast.LENGTH_SHORT) : OwnerEvents
    data class ShowSnackBar(val message: String, val duration: Int = BaseTransientBottomBar.LENGTH_SHORT) : OwnerEvents
    data class ShowError(val info : FailureInfo): OwnerEvents
    data object DoNothing : OwnerEvents
    data class LaunchActivityByCallback(val callback: (context: Context) -> Unit) : OwnerEvents // For launching activity and context based operations
    data class NavigateToComposable(val route:Int):OwnerEvents//todo
}
data class MovieUiState(
    val data: MovieModel? = null,
)