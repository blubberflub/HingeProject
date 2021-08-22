package com.example.hingeproject.user_feed.repository.source

import android.content.Context
import com.example.hingeproject.user_profile.model.UIConfig
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter
import javax.inject.Inject

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
