package com.example.presentation.model

import androidx.room.PrimaryKey

data class User(
     val email: String,
    val fullName: String,
    val NationalId: Int,
    val phoneNumber: String,
    val dateOfBirth: String,
    val password: String,
)
