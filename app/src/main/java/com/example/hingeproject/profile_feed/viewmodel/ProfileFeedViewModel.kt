package com.example.hingeproject.profile_feed.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileFeedViewModel : ViewModel() {
    val data = MutableLiveData("F")

    init {
        Log.d("testlog", "BALLS")
    }
}