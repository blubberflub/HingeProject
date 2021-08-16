package com.example.hingeproject.user_feed.repository.source

import com.example.hingeproject.user_feed.repository.model.UserProfileComposition
import javax.inject.Inject

class UserFeedDAO @Inject constructor() {
    fun getCachedProfileConfig(): UserProfileComposition {
        return UserProfileComposition(emptyList())
    }
}
