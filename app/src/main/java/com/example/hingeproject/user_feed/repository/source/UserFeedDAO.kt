package com.example.hingeproject.user_feed.repository.source

import android.content.Context
import android.content.ContextWrapper
import android.util.JsonReader
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.room.Dao
import com.example.hingeproject.user_profile.model.UIConfig
import javax.inject.Inject
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.nio.file.Files

class UserFeedDAO @Inject constructor(private val appContext: Context) {
    fun getCachedProfileConfig(): UIConfig {
        val gson = Gson()

        val reader = FileReader(appContext.filesDir.path + "UiConfig.json")
        return gson.fromJson(reader, UIConfig::class.java)
    }

    fun saveProfileConfig(response: UIConfig) {
        val gson = Gson()
        val fileWriter = FileWriter(appContext.filesDir.path + "UiConfig.json")
        gson.toJson(response, fileWriter)
        fileWriter.close()
    }
}
