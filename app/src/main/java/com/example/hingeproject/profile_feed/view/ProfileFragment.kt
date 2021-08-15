package com.example.hingeproject.profile_feed.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hingeproject.databinding.ProfileLayoutBinding
import com.example.hingeproject.profile_feed.repository.model.ProfileFeed


class ProfileFragment(val data: ProfileFeed) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = ProfileLayoutBinding.inflate(LayoutInflater.from(context), container, false)

        view.testingTextView.text = data.users.toString()

        return view.root
    }
}