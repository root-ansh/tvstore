package io.github.curioustools.tvstore

import io.github.curioustools.tvstore.api.MovieDataRepo
import io.github.curioustools.tvstore.api.MovieDataUseCase
import io.github.curioustools.tvstore.api.MovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDataUseCaseTest {

    private lateinit var repo: MovieDataRepo
    private lateinit var useCase: MovieDataUseCase

    private val fakeData = MovieModel(
        data = listOf(
            MovieModel.Categories(
                id = "1",
                title = "Action",
                subtitle = "Action-packed films",
                videos = listOf(
                    MovieModel.Categories.Video(
                        id = "v1",
                        title = "Fast & Furious",
                        url = "https://video.url"
                    )
                )
            )
        )
    )

    @Before
    fun setup() {
        repo = mock()
        useCase = MovieDataUseCase(repo)
    }

    @Test
    fun `use case returns movie data`() = runTest {
        whenever(repo.getMovieData()).thenReturn(fakeData)

        val result = useCase.execute("")

        Assert.assertEquals(fakeData, result)
        verify(repo, times(1)).getMovieData()
    }
}


