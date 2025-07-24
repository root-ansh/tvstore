package io.github.curioustools.tvstore

import io.github.curioustools.tvstore.api.MovieApi
import io.github.curioustools.tvstore.api.MovieDataRepoImpl
import io.github.curioustools.tvstore.api.MovieModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDataRepoImplTest {

    private lateinit var api: MovieApi
    private lateinit var repo: MovieDataRepoImpl

    private val fakeModel = MovieModel(
        data = listOf(
            MovieModel.Categories(
                id = "2",
                title = "Drama",
                subtitle = "Emotional stories",
                videos = listOf(
                    MovieModel.Categories.Video(
                        id = "v2",
                        title = "The Pursuit of Happyness",
                        url = "https://video.url/drama"
                    )
                )
            )
        )
    )

    @Before
    fun setup() {
        api = mock()
        repo = MovieDataRepoImpl(api)
    }

    @Test
    fun `repo returns movie data from api`() = runTest {
        whenever(api.getMovieData()).thenReturn(fakeModel)

        val result = repo.getMovieData()

        assertEquals(fakeModel, result)
        verify(api, times(1)).getMovieData()
    }

    @Test(expected = RuntimeException::class)
    fun `repo throws exception when api fails`() = runTest {
        whenever(api.getMovieData()).thenThrow(RuntimeException("API failure"))

        repo.getMovieData()
    }
}