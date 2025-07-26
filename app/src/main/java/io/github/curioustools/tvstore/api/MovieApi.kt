package io.github.curioustools.tvstore.api

import androidx.annotation.Keep
import io.github.curioustools.tvstore.base.BaseUseCase
import retrofit2.http.GET
import javax.inject.Inject

@Keep
data class MovieDTO(
    val contentRating: String = "",
    val directors: String = "",
    val fullTitle: String = "",
    val genres: String = "",
    val id: String = "",
    val image_16_9: String = "",
    val image_2_3: String = "",
    val metaCriticRating: Int = 0,
    val plot: String = "",
    val rank: Int = 0,
    val rankUpDown: String = "",
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val releaseDate: String = "",
    val runtimeMins: Int = 0,
    val runtimeStr: String = "",
    val stars: String = "",
    val subtitleUri: String = "",
    val title: String = "",
    val videoUri: String = "",
    val year: Int = 0,
){
    companion object{
        fun dtoToModel(dtos:List<MovieDTO>): MovieModel{
            val genreMap = mutableMapOf<String, MutableList<MovieDTO>>()
            for (dto in dtos) {
                val genres = dto.genres.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                for (genre in genres) {
                    genreMap.getOrPut(genre) { mutableListOf() }.add(dto)
                }
            }
            val categories = genreMap.map { MovieModel.Category(
                id = it.key,
                subtitle = getCatSubtitle(it.key),
                title = it.key.capitalize(),
                videos = it.value
            ) }

            return MovieModel(categories)
        }

        fun getCatSubtitle(title: String): String{
            return title
        }
    }
}

@Keep
data class MovieModel( val `data`: List<Category> = listOf()) {
    @Keep
    data class Category(
        val id: String = "",
        val subtitle: String = "",
        val title: String = "",
        val videos: List<MovieDTO> = listOf()
    )
}

interface MovieApi {
    @GET(NetworkDI.URL_MOVIES)
    suspend fun getMovieData(): ArrayList<MovieDTO>
}

interface MovieDataRepo{
    suspend fun getMovieData():MovieModel
}

class MovieDataRepoImpl @Inject constructor(private val movieApi: MovieApi): MovieDataRepo {
    override suspend fun getMovieData(): MovieModel {
        return MovieDTO.dtoToModel(movieApi.getMovieData())
    }
}

class MovieDataUseCase @Inject constructor(
    private val repo: MovieDataRepo
) : BaseUseCase<MovieModel, String>() {

    override suspend fun execute(params: String): MovieModel {
        return repo.getMovieData()
    }
}

