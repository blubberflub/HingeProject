package com.example.hingeproject.user_feed.repository.source

import com.example.hingeproject.user_profile.model.UIConfig
import com.example.hingeproject.user_feed.repository.model.UserFeedResponse
import retrofit2.http.GET

interface UserFeedService {
    @GET("users")
    suspend fun getProfileFeed() : UserFeedResponse

    @GET("config")
    suspend fun getProfileConfig(): UIConfig
}