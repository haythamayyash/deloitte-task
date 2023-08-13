package com.example.deloittetask.presentation.splash

import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.domain.usecase.IsUserLoggedInUseCase
import com.example.deloittetask.util.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutingViewModel @Inject constructor(private val isUserLoggedInUseCase: IsUserLoggedInUseCase) :
    BaseViewModel() {
    val navigateToNextScreen = SingleLiveData<Screen>()

    fun openNextScreenAfterDelay() = viewModelScope.launch {
        delay(DELAY_SPLASH_SCREEN)
        if (isUserLoggedInUseCase.execute() == true) {
            navigateToNextScreen.value = Screen.Home
            return@launch
        }
        navigateToNextScreen.value = Screen.Authentication
    }

    enum class Screen {
        Authentication,
        Home
    }
    companion object{
        private const val DELAY_SPLASH_SCREEN = 3000L
    }
}