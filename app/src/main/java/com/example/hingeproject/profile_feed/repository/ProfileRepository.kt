package com.example.hingeproject.profile_feed.repository

import com.example.hingeproject.profile_feed.repository.model.ProfileConfig
import com.example.hingeproject.profile_feed.repository.model.ProfileFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileFeedService: ProfileFeedService) {
    suspend fun fetchProfileFeed(): ProfileFeed = profileFeedService.getProfileFeed()
    suspend fun fetchProfileConfig(): ProfileConfig = profileFeedService.getProfileConfig()
}