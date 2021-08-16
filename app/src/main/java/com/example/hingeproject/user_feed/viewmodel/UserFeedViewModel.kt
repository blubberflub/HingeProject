package com.example.hingeproject.user_feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hingeproject.common.ErrorResponse
import com.example.hingeproject.common.NetworkError
import com.example.hingeproject.common.SuccessResponse
import com.example.hingeproject.common.UnknownError
import com.example.hingeproject.user_feed.repository.UserRepository
import com.example.hingeproject.user_feed.repository.model.UserFeed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFeedViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData(Loading)

    fun onIntent(intent: Intent) {
        when (intent) {
            Initialized -> fetchAndDisplayProfileFeed()
            FabSelected -> showNextProfile()
        }
    }

    private fun fetchAndDisplayProfileFeed() {
        viewModelScope.launch {
            launch {
                userRepository.fetchProfileConfig()
            }

            launch {
                userRepository.fetchProfileFeed().collect {
                    when (it) {
                        is SuccessResponse -> viewState.value = ProfileStackState(it.data, 0)
                        is ErrorResponse -> viewState.value = ErrorState(it.error)
                        NetworkError -> viewState.value = ErrorState("Network error has occurred!")
                        UnknownError -> viewState.value = ErrorState("Something went wrong!")
                    }
                }
            }
        }
    }

    private fun showNextProfile() {
        (viewState.value as ProfileStackState).let {
            viewState.value = ProfileStackState(it.userList, it.userInView + 1)
        }
    }
}

sealed class Intent

object Initialized : Intent()
object FabSelected : Intent()

sealed class ViewState

object Loading : ViewState()
data class ProfileStackState(val userList: UserFeed, val userInView: Int) : ViewState()
data class ErrorState(val message: String) : ViewState()