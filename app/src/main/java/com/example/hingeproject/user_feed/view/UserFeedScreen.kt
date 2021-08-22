package com.example.hingeproject.user_feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.hingeproject.R
import com.example.hingeproject.user_feed.viewmodel.FabSelected
import com.example.hingeproject.user_feed.viewmodel.UserFeedViewModel
import com.example.hingeproject.user_feed.viewmodel.ViewState
import com.example.hingeproject.user_profile.view.UserProfile
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@ExperimentalPagerApi
@Composable
fun UserFeedScreen(userFeedViewModel: UserFeedViewModel) {
    val viewState = userFeedViewModel.viewState.collectAsState(ViewState(loading = true)).value

    Scaffold(
        topBar = { TopAppBar({ Text(LocalContext.current.getString(R.string.app_title)) }) },
        content = { UserFeedContent(viewState) },
        floatingActionButton = { UserFeedFAB(viewState) { userFeedViewModel.onIntent(FabSelected) } }
    )
}

@Composable
fun UserFeedFAB(viewState: ViewState, onClick: () -> Unit) {
    if (viewState.userInView != null) {
        FloatingActionButton(onClick) {
            Image(
                painterResource(R.drawable.ic_baseline_keyboard_arrow_right_24),
                contentDescription = "fab arrow right"
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun UserFeedContent(viewState: ViewState) {
    if (viewState.loading) {
        CenteredLoadingIndicator()
    }

    if (viewState.userFeed != null && viewState.userInView != null) {
        val pagerState = remember { PagerState(pageCount = viewState.userFeed.size) }

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            UserProfile(viewState.userFeed[page])
        }

        LaunchedEffect(viewState.userInView) {
            pagerState.animateScrollToPage(
                viewState.userFeed.indexOf(viewState.userInView),
            )
        }
    }
}

@Composable
fun CenteredLoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}