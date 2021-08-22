package com.example.hingeproject

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.hingeproject.user_feed.view.UserFeedScreen
import com.example.hingeproject.user_feed.viewmodel.Initialized
import com.example.hingeproject.user_feed.viewmodel.UserFeedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val userFeedViewModel by viewModels<UserFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UserFeedScreen(userFeedViewModel)
        }
    }
}