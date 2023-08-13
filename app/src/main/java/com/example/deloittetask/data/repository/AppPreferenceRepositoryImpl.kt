package com.example.deloittetask.data.repository

import com.example.deloittetask.data.datasource.AppPreferenceDataSource
import com.example.deloittetask.domain.repository.AppPreferenceRepository
import javax.inject.Inject

class AppPreferenceRepositoryImpl @Inject constructor(private val appPreferenceDataSource: AppPreferenceDataSource) :
    AppPreferenceRepository {
    override var isLoggedIn: Boolean?
        get() = appPreferenceDataSource.isLoggedIn
        set(value) {
            appPreferenceDataSource.isLoggedIn = value
        }
    override var nationalId: Long?
        get() = appPreferenceDataSource.nationalId
        set(value) {
            appPreferenceDataSource.nationalId = value
        }

    override fun clearSession() {
        appPreferenceDataSource.clearSession()
    }
}