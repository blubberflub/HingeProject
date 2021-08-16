package com.example.hingeproject.user_feed.viewmodel

import android.util.Log
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
class ProfileFeedViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState(loading = true))

    fun onIntent(intent: Intent) {
        when (intent) {
            Initialized -> fetchAndDisplayProfileFeed()
            FabSelected -> showNextProfile()
        }
    }

    private fun fetchAndDisplayProfileFeed() {
        viewModelScope.launch {
            userRepository.refreshProfileConfig()

            launch {
                userRepository.fetchProfileFeed().collect {
                    when (it) {
                        is SuccessResponse -> viewState.value = ViewState(it.data, 0, false, null)
                        is ErrorResponse -> viewState.value = ViewState(null, null, false, it.error)
                        NetworkError -> viewState.value = ViewState(null, null, false, "Network error has occurred!")
                        UnknownError -> viewState.value = ViewState(null, null, false, "Something went wrong!")
                    }
                }
            }
        }
    }

    private fun showNextProfile() {
       viewState.value?.let {
           if (it.userInView != null) {
               viewState.value = ViewState(it.userList, it.userInView + 1)
           }
       }
    }
}

sealed class Intent

object Initialized : Intent()
object FabSelected : Intent()

data class ViewState(
    val userList: UserFeed? = null,
    val userInView: Int? = null,
    val loading: Boolean = false,
    val error: String? = null
)