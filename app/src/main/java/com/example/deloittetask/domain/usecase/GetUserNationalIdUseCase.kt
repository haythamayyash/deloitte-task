package com.example.deloittetask.domain.usecase

import com.example.deloittetask.domain.repository.AppPreferenceRepository
import javax.inject.Inject

//TODO remove this
class GetUserNationalIdUseCase @Inject constructor(private val appPreferenceRepository: AppPreferenceRepository) {
    fun execute(): Long? {
        return appPreferenceRepository.nationalId
    }
}