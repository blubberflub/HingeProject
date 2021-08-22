package com.example.hingeproject.user_feed.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.hingeproject.user_feed.repository.UserRepository
import com.example.hingeproject.user_feed.repository.model.User
import com.example.hingeproject.user_feed.repository.model.UserFeedResponse
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


@ExperimentalCoroutinesApi
class UserFeedResponseViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var userRepository: UserRepository
    @InjectMockKs
    lateinit var userFeedViewModel: UserFeedViewModel

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
    fun `given profiles - when Initialized - get profile config and show profileStack`() = runBlocking {
        //given
        val bob = User(id = 0, name = "bob")
        val andy = User(id = 1, name = "andy")
        val expectedFeed = UserFeedResponse(listOf(bob, andy))
        coEvery { userRepository.refreshProfileConfig() } just Runs
        coEvery { userRepository.fetchProfileFeed() } returns expectedFeed
        userFeedViewModel.viewState.observeForever(observer)

        //when
        userFeedViewModel.onIntent(Initialized)

        //then
        verifySequence {
            observer.onChanged(ViewState(loading = true))
            observer.onChanged(ViewState(userInView = bob, userFeed = listOf(bob, andy)))
        }
    }

    @Test
    fun `given IO exception - when Initialized - show network error message`() = runBlocking {
        //given
        coEvery { userRepository.refreshProfileConfig() } just Runs
        coEvery { userRepository.fetchProfileFeed() } throws IOException()
        userFeedViewModel.viewState.observeForever(observer)

        //when
        userFeedViewModel.onIntent(Initialized)

        //then
        verifySequence {
            observer.onChanged(ViewState(loading = true))
            observer.onChanged(ViewState(error = "Network error has occurred!"))
        }
    }

    @Test
    fun `given unknown exception - when Initialized - show generic error message`() = runBlocking {
        //given
        coEvery { userRepository.refreshProfileConfig() } just Runs
        coEvery { userRepository.fetchProfileFeed() } throws IllegalStateException()
        userFeedViewModel.viewState.observeForever(observer)

        //when
        userFeedViewModel.onIntent(Initialized)

        //then
        verifySequence {
            observer.onChanged(ViewState(loading = true))
            observer.onChanged(ViewState(error = "Something went wrong!"))
        }
    }

    @Test
    fun `given HTTP exception - when Initialized - show http error message`() = runBlocking {
        //given
        coEvery { userRepository.refreshProfileConfig() } just Runs
        coEvery { userRepository.fetchProfileFeed() } throws HttpException(
            Response.error<Any>(
                500,
                ResponseBody.create(MediaType.parse("text/plain"), "Test Server Error")
            )
        )
        userFeedViewModel.viewState.observeForever(observer)

        //when
        userFeedViewModel.onIntent(Initialized)

        //then
        verifySequence {
            observer.onChanged(ViewState(loading = true))
            observer.onChanged(ViewState(error = "500 - Test Server Error"))
        }
    }

    @Test
    fun `given 2 users and first in view - when FAB selected - show next user and show next`() = runBlocking {
        //given
        val bob = User(id = 0, name = "bob")
        val andy = User(id = 1, name = "andy")
        val userFeed = listOf(bob, andy)
        userFeedViewModel.viewState.value = ViewState(userFeed, bob)
        userFeedViewModel.userFeedIterator = userFeed.listIterator().apply { next() }
        userFeedViewModel.viewState.observeForever(observer)

        //when
        userFeedViewModel.onIntent(FabSelected)

        //then
        verifySequence {
            observer.onChanged(ViewState(userFeed = userFeed, userInView = bob))
            observer.onChanged(ViewState(userFeed = userFeed, userInView = andy))
        }
    }
}