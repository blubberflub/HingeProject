package com.example.hingeproject.user_feed.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.hingeproject.user_feed.repository.model.User
import androidx.compose.ui.text.style.TextAlign.Companion as TextAlign1


class ProfileFragment(val data: User) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        val view = ProfileLayoutBinding.inflate(LayoutInflater.from(context), container, false)

//        view.testingTextView.text = data.about
        return ComposeView(requireContext()).apply {
            setContent {
                UserProfile(data)
            }
        }
    }
}

@Composable
fun UserProfile(data: User) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = data.name,
            fontSize = 30.sp,
            textAlign = TextAlign1.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .padding(25.dp)
        )
    }
}

@Preview
@Composable
fun ComposablePreview() {
    UserProfile(
        data = User(
            name = "Jim",
            about = "testing about",
            gender = "m",
            hobbies = emptyList(),
            id = 1,
            photo = "",
            school = ""
        )
    )
}