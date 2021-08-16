package com.example.hingeproject.user_profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.example.hingeproject.R
import com.example.hingeproject.user_feed.repository.model.User
import com.example.hingeproject.user_profile.viewmodel.Initialized
import com.example.hingeproject.user_profile.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    companion object {
        fun newInstance(user: User) = UserProfileFragment().apply {
            arguments = bundleOf(Pair("user", user))
        }
    }

    private val profileViewModel: UserProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        profileViewModel.onIntent(Initialized)

        return ComposeView(requireContext()).apply {
            setContent {
                UserProfile(profileViewModel)
            }
        }
    }
}

@Composable
fun UserProfile(viewModel: UserProfileViewModel) {
    val state = viewModel.viewState.observeAsState()
    val uiConfig = state.value?.uiConfig
    val user = state.value?.user
    val scrollState = rememberScrollState()

    if (uiConfig == null) {
        return
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
        uiConfig.profile.forEach {
            when (it) {
                "name" -> NameSection(user?.name)
                "photo" -> PhotoSection(user?.photo)
                "gender" -> GenderSection(user?.gender)
                "about" -> AboutSection(user?.about)
                "school" -> SchoolSection(user?.school)
                "hobbies" -> HobbiesSection(user?.hobbies)
            }
        }
    }
}

@Composable
fun NameSection(name: String?) {
    if (name == null) {
        return
    }

    Text(
        text = name,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 25.dp)
    )
}

@Composable
fun PhotoSection(photo: String?) {
    if (photo == null) {
        Image(
            painter = rememberImagePainter(data = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(400.dp)
        )
        return
    }

    Image(
        painter = rememberImagePainter(data = photo),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    )
}

@Composable
fun GenderSection(gender: String?) {
    if (gender == null) {
        return
    }

    Text(
        text = "Gender",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 25.dp)
    )
    Text(
        text = gender,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 25.dp)
    )
}

@Composable
fun AboutSection(about: String?) {
    if (about == null) {
        return
    }

    Text(
        text = "About",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 25.dp)
    )
    Text(
        text = about,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 25.dp)
    )
}

@Composable
fun SchoolSection(school: String?) {
    if (school == null) {
        return
    }

    Text(
        text = "School",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 25.dp)
    )
    Text(
        text = school,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 25.dp)
    )
}

@Composable
fun HobbiesSection(hobbies: List<String>?) {
    if (hobbies == null) {
        return
    }

    Text(
        text = "Hobbies",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 25.dp)
    )
    hobbies.forEach {
        Text(
            text = it,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 25.dp)
        )
    }
}