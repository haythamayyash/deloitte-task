package com.example.deloittetask.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val email: String,
    val fullName: String,
    @PrimaryKey val nationalId: Long,
    val phoneNumber: String,
    val dateOfBirth: String,
    val password: String,
)
