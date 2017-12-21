package com.rudearts.reposearcher.util

import android.content.Context
import android.content.SharedPreferences

class Preferences (context: Context) : Preferencable {

    companion object {
        val PREFS_FILENAME = "com.rudearts.reposearcher"
        val FAVORITES = "favorites"
    }

    internal val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    override var favorites: String
        get() = prefs.getString(FAVORITES, "")
        set(value) = prefs.edit().putString(FAVORITES, value).apply()
}
