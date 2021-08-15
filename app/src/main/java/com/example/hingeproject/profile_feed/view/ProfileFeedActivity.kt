package com.example.hingeproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hingeproject.databinding.ActivityMainBinding
import com.example.hingeproject.profile_feed.repository.model.ProfileFeed
import com.example.hingeproject.profile_feed.view.ProfileFragment
import com.example.hingeproject.profile_feed.viewmodel.FabSelected
import com.example.hingeproject.profile_feed.viewmodel.Initialized
import com.example.hingeproject.profile_feed.viewmodel.ProfileFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var profileAdapter: ProfilePagerAdapter
    val profileViewModel: ProfileFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        initializeViewPager()
        initializeFab()
        initializeViewModel()

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.app_title)
    }

    private fun initializeViewModel() {
        profileViewModel.viewState.observe(this) {
//            profileAdapter.submitList(it)
        }

        profileViewModel.onIntent(Initialized)
    }

    private fun initializeViewPager() {
        profileAdapter = ProfilePagerAdapter(this)
        viewPager = binding.pager
        viewPager.isUserInputEnabled = false
        viewPager.setPageTransformer { page, _ ->
            page.alpha = 0f;
            page.visibility = View.VISIBLE;

            // Start Animation for a short period of time
            page.animate()
                .alpha(1f)
                .setDuration(200)
        }
        viewPager.adapter = profileAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeFab() {
        binding.profileFab.setOnClickListener {
            profileViewModel.onIntent(FabSelected)
        }
    }
}

private class ProfilePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    val profiles = mutableListOf<ProfileFeed>()

    override fun createFragment(position: Int) = ProfileFragment(profiles[position])
    override fun getItemCount() = profiles.size


    fun submitList(newList: List<ProfileFeed>) {
        profiles.clear()
        profiles.addAll(newList)
        notifyDataSetChanged()
    }
}