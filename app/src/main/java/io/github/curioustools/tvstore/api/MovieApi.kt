package io.github.curioustools.tvstore.api

import androidx.annotation.Keep
import io.github.curioustools.tvstore.base.BaseResponse
import io.github.curioustools.tvstore.base.BaseUseCase
import io.github.curioustools.tvstore.base.executeAndUnify
import retrofit2.Call
import retrofit2.http.GET
import javax.inject.Inject

@Keep
data class MovieModel(
    val answer: String = "",
    val category: String = "",
    val choices: List<String> = listOf(),
    val question: String = ""
)

interface MovieApi {

    @GET(NetworkDI.URL_QUIZ)
    suspend fun getMovieData(): Call<MovieModel>

}

interface MovieDataRepo{
    suspend fun getMovieData():BaseResponse<MovieModel>
}

class MovieDataRepoImpl @Inject constructor(private val movieApi: MovieApi): MovieDataRepo {
    override suspend fun getMovieData(): BaseResponse<MovieModel> {
        return movieApi.getMovieData().executeAndUnify()
    }
}

class MovieDataUseCase @Inject constructor(
    private val repo: MovieDataRepo
) : BaseUseCase<BaseResponse<MovieModel>, String>() {

    override suspend fun execute(params: String): BaseResponse<MovieModel> {
        return repo.getMovieData()
    }
}

