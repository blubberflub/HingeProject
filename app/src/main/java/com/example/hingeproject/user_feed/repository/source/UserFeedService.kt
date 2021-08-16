package com.example.hingeproject.user_feed.repository.source

import com.example.hingeproject.user_feed.repository.model.UserProfileComposition
import com.example.hingeproject.user_feed.repository.model.UserFeed
import retrofit2.http.GET

interface UserFeedService {
    @GET("users")
    suspend fun getProfileFeed() : UserFeed

    @GET("config")
    suspend fun getProfileConfig(): UserProfileComposition
}