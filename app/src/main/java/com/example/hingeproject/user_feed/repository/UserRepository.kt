package com.example.hingeproject.user_feed.repository

import com.example.hingeproject.user_feed.repository.model.UserFeedResponse
import com.example.hingeproject.user_feed.repository.source.UserFeedDAO
import com.example.hingeproject.user_feed.repository.source.UserFeedService
import com.example.hingeproject.user_profile.model.UIConfig
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userFeedService: UserFeedService,
    private val userFeedDao: UserFeedDAO
) {
    suspend fun fetchProfileFeed(): UserFeedResponse = userFeedService.getProfileFeed()

    fun getProfileConfig(): UIConfig = userFeedDao.getCachedProfileConfig()

    suspend fun refreshProfileConfig() {
        val response = userFeedService.getProfileConfig()
        userFeedDao.saveProfileConfig(response)
    }
}