package com.example.hingeproject.user_feed.repository.model

data class User(
    val about: String? = null,
    val gender: String? = null,
    val hobbies: List<String>? = null,
    val id: Int,
    val name: String,
    val photo: String? = null,
    val school: String? = null
)