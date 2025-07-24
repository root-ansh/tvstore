package io.github.curioustools.tvstore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.curioustools.tvstore.api.MovieDataUseCase
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.NothingSerializer
import javax.inject.Inject

@HiltViewModel
class MovieDataViewModel @Inject constructor(private val movieDataUseCase: MovieDataUseCase) : ViewModel() {

    fun requestData(){
        viewModelScope.launch {
           try {
               val data = movieDataUseCase.execute("")
               println(data)
           }
           catch (t: Throwable){

           }

        }
    }

}