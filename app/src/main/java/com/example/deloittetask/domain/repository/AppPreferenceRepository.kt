package com.example.deloittetask.domain.repository

interface AppPreferenceRepository {
    var isLoggedIn: Boolean?
    var nationalId: Long?
}