package com.example.hingeproject.hilt

import com.example.hingeproject.user_feed.repository.source.UserFeedService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    private companion object {
        private const val BASE_URL = "http://hinge-ue1-dev-cli-android-homework.s3-website-us-east-1.amazonaws.com/"
    }

    @Provides
    fun providesRetroFit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesProfileFeedService(retrofit: Retrofit): UserFeedService {
        return retrofit.create(UserFeedService::class.java)
    }
}