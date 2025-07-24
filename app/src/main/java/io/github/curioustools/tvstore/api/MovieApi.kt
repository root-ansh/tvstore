package io.github.curioustools.tvstore.api

import androidx.annotation.Keep
import io.github.curioustools.tvstore.base.BaseUseCase
import retrofit2.http.GET
import javax.inject.Inject

@Keep
data class MovieModel( val `data`: List<Categories> = listOf()) {
    @Keep
    data class Categories(
        val id: String = "",
        val subtitle: String = "",
        val title: String = "",
        val videos: List<Video> = listOf()
    ) {
        @Keep
        data class Video(
            val background: String = "",
            val description: String = "",
            val id: String = "",
            val tags: List<String> = listOf(),
            val thumbnail: String = "",
            val title: String = "",
            val url: String = ""
        )
    }
}

interface MovieApi {

    @GET(NetworkDI.URL_MOVIES)
    suspend fun getMovieData(): MovieModel

}

interface MovieDataRepo{
    suspend fun getMovieData():MovieModel
}

class MovieDataRepoImpl @Inject constructor(private val movieApi: MovieApi): MovieDataRepo {
    override suspend fun getMovieData(): MovieModel {
        return  movieApi.getMovieData()
    }
}

class MovieDataUseCase @Inject constructor(
    private val repo: MovieDataRepo
) : BaseUseCase<MovieModel, String>() {

    override suspend fun execute(params: String): MovieModel {
        return repo.getMovieData()
    }
}

