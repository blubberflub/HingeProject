package com.example.hingeproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.hingeproject.databinding.ActivityMainBinding
import com.example.hingeproject.profile_feed.model.Testing
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hingeproject.profile_feed.view.ProfileFragment
import com.example.hingeproject.profile_feed.viewmodel.ProfileFeedViewModel


class ProfileFeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var profileAdapter: ProfilePagerAdapter
    val profileViewModel by viewModels<ProfileFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        initializeViewPager()
        initializeFab()

        profileViewModel.data.value

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.app_title)

        profileAdapter.submitList(listOf(Testing("132"), Testing("234")))
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

    private fun initializeFab() {
        binding.profileFab.setOnTouchListener { view, motionEvent ->

            false
        }
    }
}

private class ProfilePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    val profiles = mutableListOf<Testing>()

    override fun createFragment(position: Int) = ProfileFragment(profiles[position])
    override fun getItemCount() = profiles.size


    fun submitList(newList: List<Testing>) {
        profiles.clear()
        profiles.addAll(newList)
        notifyDataSetChanged()
    }
}