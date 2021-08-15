package com.example.hingeproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.hingeproject.databinding.ActivityMainBinding
import com.example.hingeproject.model.Testing

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = BasicListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.app_title)

        binding.profileRecyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }
        binding.profileRecyclerView.adapter = adapter

        adapter.submitList(listOf(Testing("132"), Testing("234")))
    }
}