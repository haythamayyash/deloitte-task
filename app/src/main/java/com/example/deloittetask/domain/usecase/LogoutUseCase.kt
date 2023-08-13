package com.example.deloittetask.domain.usecase

import com.example.deloittetask.domain.repository.AppPreferenceRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val appPreferenceRepository: AppPreferenceRepository
) {
    fun execute() {
        appPreferenceRepository.clearSession()
    }
}