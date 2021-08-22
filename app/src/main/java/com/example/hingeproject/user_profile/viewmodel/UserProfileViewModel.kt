package com.example.hingeproject.user_profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hingeproject.user_feed.repository.UserRepository
import com.example.hingeproject.user_profile.model.Sections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    companion object {
        val DEFAULT_SECTIONS = listOf(
            Sections.NAME,
            Sections.PHOTO,
            Sections.GENDER,
            Sections.ABOUT,
            Sections.SCHOOL,
            Sections.HOBBIES
        )
    }

    val viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState(DEFAULT_SECTIONS))

    init {
        onIntent(Initialized)
    }

    fun onIntent(intent: Intent) {
        when(intent) {
            Initialized -> fetchAndDisplayUserProfileComposition()
        }
    }

    private fun fetchAndDisplayUserProfileComposition() {
        viewModelScope.launch {
            try {
                val uiConfig = userRepository.getProfileConfig().profile
                    .map { it.uppercase() }
                    .filter { validSection(it) }
                    .map { enumValueOf<Sections>(it) }

                viewState.value = ViewState(uiConfig)
            } catch(throwable: Throwable) {
                //Intentionally not show error to user if uiConfig is not fetched
            }
        }
    }

    private fun validSection(sectionString: String): Boolean {
        return Sections.values()
            .contains(enumValueOf(sectionString))
            .also { contains ->
                if (!contains) {
                    Log.e(UserProfileViewModel::class.simpleName, "Cannot render section: $sectionString")
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

sealed class Intent

object Initialized : Intent()

data class ViewState(val uiConfig: List<Sections>)
