package com.example.deloittetask.data.datasource

import androidx.room.Insert
import androidx.room.Query
import com.example.deloittetask.domain.model.User

interface AppPreferenceDataSource {
    var isLoggedIn: Boolean?
    var nationalId: Long?
    fun clearSession()
}