package com.example.hingeproject.user_profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hingeproject.user_feed.repository.UserRepository
import com.example.hingeproject.user_feed.repository.model.User
import com.example.hingeproject.user_profile.model.UIConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(val savedStateHandle: SavedStateHandle, val userRepository: UserRepository) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    fun onIntent(intent: Intent) {
        when (intent) {
            Initialized -> fetchAndDisplayUserProfileComposition()
        }
    }

    private fun fetchAndDisplayUserProfileComposition() {
        viewModelScope.launch {
            val uiConfig = userRepository.getProfileConfig()

            savedStateHandle.get<User>("user")?.also {
                viewState.value = ViewState(uiConfig, it)
            }
        }
//        userRepository.getProfileConfig()
    }
}

sealed class Intent

object Initialized : Intent()

data class ViewState(val uiConfig: UIConfig, val user: User)
