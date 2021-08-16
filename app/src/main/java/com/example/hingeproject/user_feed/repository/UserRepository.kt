package com.example.hingeproject.user_feed.repository

import android.util.Log
import com.example.hingeproject.common.*
import com.example.hingeproject.user_feed.repository.model.UserFeed
import com.example.hingeproject.user_feed.repository.source.UserFeedDAO
import com.example.hingeproject.user_feed.repository.source.UserFeedService
import com.example.hingeproject.user_profile.model.UIConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userFeedService: UserFeedService,
    private val userFeedDao: UserFeedDAO
) {
    suspend fun fetchProfileFeed(): Flow<ResponseWrapper<UserFeed>> {
        return flow {
            try {
                emit(SuccessResponse(userFeedService.getProfileFeed()))
            } catch (throwable: Throwable) {
                handleError(throwable)
            }
        }
    }

    fun getProfileConfig(): UIConfig {
        return userFeedDao.getCachedProfileConfig()
    }

    suspend fun refreshProfileConfig() {
        try {
            val response = userFeedService.getProfileConfig()

            userFeedDao.saveProfileConfig(response)
        } catch (throwable: Throwable) {
            handleError(throwable)
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is IOException -> NetworkError
            is HttpException -> {
                ErrorResponse(throwable.code(), throwable.message())
            }
            else -> {
                Log.e(this::class.simpleName, "Unknown error occured {$throwable}")
                UnknownError
            }
        }
    }
}