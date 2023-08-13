package com.example.deloittetask.data

import androidx.room.Insert
import androidx.room.Query
import com.example.deloittetask.domain.model.User

interface AppPreferenceDataSource {
    var isLoggedIn: Boolean?
    var nationalId: Long?
}