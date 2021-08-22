package com.example.hingeproject.user_profile.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.hingeproject.user_feed.repository.UserRepository
import com.example.hingeproject.user_feed.repository.model.User
import com.example.hingeproject.user_profile.model.Sections.*
import com.example.hingeproject.user_profile.model.UIConfig
import com.example.hingeproject.user_profile.viewmodel.UserProfileViewModel.Companion.DEFAULT_SECTIONS
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserProfileViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var savedStateHandle: SavedStateHandle
    @MockK
    lateinit var userRepository: UserRepository
    @InjectMockKs
    lateinit var userProfileViewModel: UserProfileViewModel

    @MockK
    lateinit var observer: Observer<ViewState>

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
        every { observer.onChanged(any()) } just Runs
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given user and ui config - when Initialized - show user in ui config order`() {
        //given
        val bob = User(id = 0, name = "bob")
        every { savedStateHandle.get<User>("user") } returns bob
        coEvery { userRepository.getProfileConfig() } returns UIConfig(DEFAULT_SECTIONS.map { it.name })
        userProfileViewModel.viewState.observeForever(observer)

        //when
        userProfileViewModel.onIntent(Initialized)

        //then
        verify { observer.onChanged(ViewState(DEFAULT_SECTIONS)) }
    }

    @Test
    fun `given user and different order ui config - when Initialized - show user in ui config order`() {
        //given
        val bob = User(id = 0, name = "bob")
        val experimentUiConfig = UIConfig(listOf("school", "name", "about", "photo", "hobbies", "gender"))
        every { savedStateHandle.get<User>("user") } returns bob
        coEvery { userRepository.getProfileConfig() } returns experimentUiConfig
        userProfileViewModel.viewState.observeForever(observer)

        //when
        userProfileViewModel.onIntent(Initialized)

        //then
        val expectedUI = listOf(SCHOOL, NAME, ABOUT, PHOTO, HOBBIES, GENDER)
        verify { observer.onChanged(ViewState(expectedUI)) }
    }
}