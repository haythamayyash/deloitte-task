package com.example.presentation.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BaseViewModel
import com.example.domain.usecase.LoginUseCase
import com.example.util.DeloitteError
import com.example.util.mapToDeloitteError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    BaseViewModel() {

    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState> = _viewState


    fun login(email: String, password: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _viewState.value = LoginViewState.Failure(throwable.mapToDeloitteError())
        }

        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                _viewState.value = LoginViewState.Loading
                loginUseCase.execute(email, password)
                _viewState.value = LoginViewState.Success
            }
        }
    }

    sealed class LoginViewState {
        object Loading : LoginViewState()
        object Success : LoginViewState()
        data class Failure(val deloitteError: DeloitteError) : LoginViewState()
    }
}