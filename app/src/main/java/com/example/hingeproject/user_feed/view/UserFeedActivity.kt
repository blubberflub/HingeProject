package com.example.hingeproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hingeproject.databinding.ActivityUserFeedBinding
import com.example.hingeproject.user_feed.repository.model.User
import com.example.hingeproject.user_feed.repository.model.UserFeed
import com.example.hingeproject.user_feed.viewmodel.*
import com.example.hingeproject.user_profile.view.UserProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFeedBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var profileAdapter: ProfilePagerAdapter
    val profileViewModel: ProfileFeedViewModel by viewModels()

    private fun render(viewState: ViewState) {
        Log.d("testlog", viewState.toString())
        showProgressBar(viewState.loading)
        if (viewState.userList != null && viewState.userInView != null) {
            showProfileStack(viewState.userList, viewState.userInView)
        }
        if (viewState.error != null) {
            showError(viewState.error)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserFeedBinding.inflate(layoutInflater)

        initializeViewPager()
        initializeFab()
        initializeViewModel()

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.app_title)
    }

    private fun initializeViewModel() {
        profileViewModel.viewState.observe(this) {
            render(it)
        }

        profileViewModel.onIntent(Initialized)
    }

    private fun showProfileStack(userFeed: UserFeed, userInView: Int) {
        profileAdapter.submitList(userFeed.users)
        viewPager.currentItem = userInView
    }


    private fun showProgressBar(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

private class ProfilePagerAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {
    val profiles = mutableListOf<User>()

    override fun createFragment(position: Int) =
        UserProfileFragment.newInstance(profiles[position])

    override fun getItemCount() = profiles.size


    fun submitList(newList: List<User>) {
        profiles.clear()
        profiles.addAll(newList)
        notifyDataSetChanged()
    }
}