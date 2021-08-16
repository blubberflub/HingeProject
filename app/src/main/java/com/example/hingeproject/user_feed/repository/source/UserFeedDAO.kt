package com.example.hingeproject.user_feed.repository.source

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.room.Dao
import com.example.hingeproject.user_profile.model.UIConfig
import javax.inject.Inject
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileReader


@Dao
class UserFeedDAO @Inject constructor(val appContext: Context) {
    fun getCachedProfileConfig(): UIConfig {
        val gson = Gson()

        val uiConfigJson = PreferenceManager.getDefaultSharedPreferences(appContext).getString("uiConfig", "")
        return gson.fromJson(uiConfigJson, UIConfig::class.java)
    }

    fun saveProfileConfig(response: UIConfig) {
        val gson = Gson()
        val uiConfigJson = gson.toJson(response)

        PreferenceManager.getDefaultSharedPreferences(appContext)
            .edit()
            .putString("uiConfig", uiConfigJson)
            .apply()

        Log.d("testlog", "SAVED TO PREFERENCES")
    }
}
