package io.github.curioustools.tvstore.api

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {

    companion object {
        private const val KEY_FAVOURITES = "favourites"
        private const val KEY_RECENT_VIDEOS = "recents"
        private const val KEY_RECENT_SEARCHES = "searches"

    }

    var favouriteIds: String
        get() = prefs.getString(KEY_FAVOURITES, "").orEmpty()
        set(value) {
            prefs.edit { putString(KEY_FAVOURITES, value) }
        }

    var listingCache: MovieModel?
        get() = prefs.getString(KEY_FAVOURITES, "").orEmpty().toModelViaGSON<MovieModel>()
        set(value) {
            prefs.edit { putString(KEY_FAVOURITES, value.toJsonString()) }
        }



    fun clear() {
        prefs.edit { clear() }
    }
}
inline fun <reified T> String.toModelViaGSON(): T?{
    return runCatching {
        val type: Type = object : TypeToken<T>() {}.type
        val gson = Gson()
        return gson.fromJson(this,type)
    }.getOrNull()
}
inline fun <reified T> T.toJsonString(): String {
    return try {
        Gson().toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        "{}"
    }
}