package com.example.hingeproject.user_profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.hingeproject.R
import com.example.hingeproject.user_feed.repository.model.User
import com.example.hingeproject.user_profile.model.Sections
import com.example.hingeproject.user_profile.viewmodel.UserProfileViewModel
import com.example.hingeproject.user_profile.viewmodel.UserProfileViewModel.Companion.DEFAULT_SECTIONS
import com.example.hingeproject.user_profile.viewmodel.ViewState

@Composable
fun UserProfile(user: User, userProfileViewModel: UserProfileViewModel = viewModel()) {
    val state = userProfileViewModel.viewState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
        state.value.uiConfig.forEach {
            when (it) {
                Sections.NAME -> NameSection(user.name)
                Sections.PHOTO -> PhotoSection(user.photo)
                Sections.GENDER -> GenderSection(user.gender)
                Sections.ABOUT -> AboutSection(user.about)
                Sections.SCHOOL -> SchoolSection(user.school)
                Sections.HOBBIES -> HobbiesSection(user.hobbies)
            }
        }
    }
}

@Composable
fun NameSection(name: String) {
    Text(
        text = name,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp)
    )
}

@Composable
fun PhotoSection(photo: String?) {
    if (photo == null) {
        Image(
            painter = rememberImagePainter(data = R.drawable.ic_launcher_foreground),
            contentDescription = null,
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