package com.example.hingeproject.user_feed.repository

import android.util.Log
import com.example.hingeproject.common.ErrorResponse
import com.example.hingeproject.common.NetworkError
import com.example.hingeproject.common.ResponseWrapper
import com.example.hingeproject.common.SuccessResponse
import com.example.hingeproject.common.UnknownError
import com.example.hingeproject.user_feed.repository.model.UserProfileComposition
import com.example.hingeproject.user_feed.repository.model.UserFeed
import com.example.hingeproject.user_feed.repository.source.UserFeedDAO
import com.example.hingeproject.user_feed.repository.source.UserFeedService
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

    suspend fun fetchProfileConfig(): Flow<ResponseWrapper<UserProfileComposition>> {
        return flow {
            try {
                emit(SuccessResponse(userFeedDao.getCachedProfileConfig()))
                emit(SuccessResponse(userFeedService.getProfileConfig()))
            } catch (throwable: Throwable) {
                handleError(throwable)
            }
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