package io.github.curioustools.tvstore.api

import android.content.SharedPreferences
import androidx.core.content.edit
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

    var recentVideos: String
        get() = prefs.getString(KEY_RECENT_VIDEOS, "").orEmpty()
        set(value) {
            prefs.edit { putString(KEY_RECENT_VIDEOS, value) }
        }

    var searches: String
        get() = prefs.getString(KEY_RECENT_SEARCHES, "").orEmpty()
        set(value) {
            prefs.edit { putString(KEY_RECENT_SEARCHES, value) }
        }


    fun clear() {
        prefs.edit { clear() }
    }
}
