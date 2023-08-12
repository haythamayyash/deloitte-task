package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val email: String,
    val fullName: String,
    val NationalId: Int,
    val phoneNumber: String,
    val dateOfBirth: String,
    val password: String,
)
