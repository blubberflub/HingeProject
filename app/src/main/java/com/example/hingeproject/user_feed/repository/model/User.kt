package com.example.hingeproject.user_feed.repository.model

data class User(
    val about: String,
    val gender: String,
    val hobbies: List<String>,
    val id: Int,
    val name: String,
    val photo: String,
    val school: String
)