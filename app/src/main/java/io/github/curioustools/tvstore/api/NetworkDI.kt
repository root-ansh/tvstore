package io.github.curioustools.tvstore.api

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDI{
    const val URL_BASE = "https://raw.githubusercontent.com/"
    const val URL_MOVIES = "/root-ansh/misc_test_server/refs/heads/master/ott/ott_resp.json"
    @Provides
    fun makeHttpCache(@ApplicationContext context: Context): Cache {
        return Cache(context.applicationContext.cacheDir, 5 * 1024 * 1024L)
    }

    @Provides
    fun makeOkHttpClient(cache: Cache): OkHttpClient {
        val logger = HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .cache(cache)
            .retryOnConnectionFailure(true)
            //.addInterceptor(logger)
            .connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Provides
    fun makeGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun makeRetrofitService(gson: Gson, okHttp: OkHttpClient) : Retrofit {
        val gsonFactory = GsonConverterFactory.create(gson)
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(okHttp)
            .addConverterFactory(gsonFactory)
            .build()
    }


    @Provides
    fun makeMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun makeMovieRepo(service: MovieApi): MovieDataRepo {
        return MovieDataRepoImpl(service)
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

}