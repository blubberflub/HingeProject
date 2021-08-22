package com.example.hingeproject.user_feed.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hingeproject.user_feed.repository.UserRepository
import com.example.hingeproject.user_feed.repository.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserFeedViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    var viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState(loading = true))
    private var users = mutableListOf<User>()
    var userFeedIterator: ListIterator<User>? = null

    init {
        onIntent(Initialized)
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            Initialized -> fetchAndDisplayProfileFeed()
            FabSelected -> showNextProfile()
        }
    }

    private fun fetchAndDisplayProfileFeed() {
        viewModelScope.launch {
            try {
                userRepository.refreshProfileConfig()
                val userFeedResponse = userRepository.fetchProfileFeed()

                users.addAll(userFeedResponse.users)
                userFeedIterator = userFeedResponse.users.listIterator()

                viewState.value = ViewState(users, userFeedIterator?.next())
            } catch (throwable: Throwable) {
                handleError(throwable)
            }
        }
    }

    private fun showNextProfile() {
        userFeedIterator?.let {
            if (!it.hasNext()) {
                viewState.value = ViewState(loading = true)
                return
            }

            viewState.value = viewState.value.copy(userInView = it.next())
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is IOException -> viewState.value = ViewState(error = "Network error has occurred!")
            is HttpException -> {
                val code = throwable.code()
                val message = throwable.response()?.errorBody()?.string() ?: "Error"

                viewState.value = ViewState(error = "$code - $message")
            }
            else -> viewState.value = ViewState(error = "Something went wrong!")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

sealed class Intent

object Initialized : Intent()

object FabSelected : Intent()

data class ViewState(
    val userFeed: List<User>? = null,
    val userInView: User? = null,
    val loading: Boolean = false,
    val error: String? = null
)