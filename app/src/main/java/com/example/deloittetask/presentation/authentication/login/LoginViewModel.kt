package com.example.deloittetask.presentation.authentication.login

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.domain.usecase.LoginUseCase
import com.example.deloittetask.presentation.authentication.registration.RegistrationViewModel
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.SingleLiveData
import com.example.deloittetask.util.mapToDeloitteError
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
    val events = SingleLiveData<LoginEvent>()


    fun login(email: String, password: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is WrongEmailOrPasswordException) {
                _viewState.value = LoginViewState.Failure(
                    DeloitteError.LocalError(ERROR_CODE_WRONG_EMAIL_OR_PASSWORD)
                )
                return@CoroutineExceptionHandler
            }
            _viewState.value = LoginViewState.Failure(throwable.mapToDeloitteError())
        }

        viewModelScope.launch(exceptionHandler) {
            _viewState.value = LoginViewState.Loading
            withContext(Dispatchers.IO) {
                val user = loginUseCase.execute(email, password) ?: throw WrongEmailOrPasswordException()
            }
            _viewState.value = LoginViewState.Success
            events.value = LoginEvent.OpenHome
        }
    }

    sealed class LoginViewState {
        object Loading : LoginViewState()
        object Success : LoginViewState()
        data class Failure(val deloitteError: DeloitteError) : LoginViewState()
    }

    sealed class LoginEvent {
        object OpenHome : LoginEvent()
    }
    companion object{
        const val ERROR_CODE_WRONG_EMAIL_OR_PASSWORD = 1556
    }
    class WrongEmailOrPasswordException: Exception()
}