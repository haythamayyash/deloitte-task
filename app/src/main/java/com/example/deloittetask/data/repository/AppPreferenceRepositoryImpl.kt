package com.example.deloittetask.data.repository

import com.example.deloittetask.data.AppPreferenceDataSource
import com.example.deloittetask.domain.repository.AppPreferenceRepository
import javax.inject.Inject

class AppPreferenceRepositoryImpl @Inject constructor(private val appPreferenceDataSource: AppPreferenceDataSource) :
    AppPreferenceRepository {
    override var isLoggedIn: Boolean? = appPreferenceDataSource.isLoggedIn
    override var nationalId: Long? = appPreferenceDataSource.nationalId
}