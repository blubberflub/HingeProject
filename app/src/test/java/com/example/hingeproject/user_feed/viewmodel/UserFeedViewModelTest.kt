package com.example.hingeproject.user_feed.viewmodel

import com.example.hingeproject.user_feed.repository.UserRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserFeedViewModelTest() {
    @MockK
    lateinit var userRepository: UserRepository
    @InjectMockKs
    lateinit var profileFeedViewModel: ProfileFeedViewModel

    @Test
    fun givenProfiles_whenFabSelectedIntent_showNextProfile() {
        //given
//        every { profileRepository.fetchProfileFeed() } returns listOf(ProfileFeed("123"), ProfileFeed("456"))

        //when
        profileFeedViewModel.onIntent(FabSelected)

        //then

    }
}