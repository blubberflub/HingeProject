package com.example.hingeproject.profile_feed.repository

import com.example.hingeproject.profile_feed.repository.model.ProfileConfig
import com.example.hingeproject.profile_feed.repository.model.ProfileFeed
import retrofit2.http.GET

interface ProfileFeedService {
    @GET("users")
    suspend fun getProfileFeed() : ProfileFeed

    @GET("config")
    suspend fun getProfileConfig(): ProfileConfig
}