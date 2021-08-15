package com.example.hingeproject.profile_feed.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.hingeproject.profile_feed.repository.ProfileRepository
import com.example.hingeproject.profile_feed.repository.model.ProfileConfig
import com.example.hingeproject.profile_feed.repository.model.ProfileFeed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class ProfileFeedViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    val viewState: LiveData<ViewState> = MutableLiveData(Loading)

    fun onIntent(intent: Intent) {
        when (intent) {
            Initialized -> fetchAndDisplayProfileFeed()
            FabSelected -> showNextProfile()
        }
    }

    private fun fetchAndDisplayProfileFeed() {
        viewModelScope.launch {
            val profileFeed = async {
                try {
                    profileRepository.fetchProfileFeed()
                } catch (ex: Exception) {
                    ProfileFeed(emptyList())
                }
            }

            val profileConfig = async {
                try {
                    profileRepository.fetchProfileConfig()
                } catch (ex: Exception) {
                    //Don't show any error to user when this fails
                }
            }

            Log.d("testlog", profileFeed.await().toString())
            Log.d("testlog", profileConfig.toString())
        }
    }

    private fun showNextProfile() {

    }
}

sealed class Intent

object Initialized : Intent()
object FabSelected : Intent()

sealed class ViewState

object Loading : ViewState()

data class ProfileStack(val profileList: List<ProfileFeed>) : ViewState()
object Error : ViewState()