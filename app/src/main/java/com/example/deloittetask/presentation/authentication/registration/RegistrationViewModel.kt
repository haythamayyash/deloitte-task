package com.example.deloittetask.presentation.authentication.registration

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.usecase.RegisterUseCase
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
class RegistrationViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    BaseViewModel() {


    private val _viewState = MutableLiveData<RegistrationViewState>()
    val viewState: LiveData<RegistrationViewState> = _viewState
    val events = SingleLiveData<RegistrationEvent>()


    fun register(user: User) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is SQLiteConstraintException) {
                _viewState.value = RegistrationViewState.Failure(
                    DeloitteError.LocalError(ERROR_CODE_ALREADY_EXIST_USER)
                )
                return@CoroutineExceptionHandler
            }
            _viewState.value = RegistrationViewState.Failure(throwable.mapToDeloitteError())
        }

        viewModelScope.launch(exceptionHandler) {
            _viewState.value = RegistrationViewState.Loading
            withContext(Dispatchers.IO) {
                registerUseCase.execute(user)
            }
            _viewState.value = RegistrationViewState.Success
            events.value = RegistrationEvent.OpenHome
        }
    }

    sealed class RegistrationViewState {
        object Loading : RegistrationViewState()
        object Success : RegistrationViewState()
        data class Failure(val deloitteError: DeloitteError) : RegistrationViewState()
    }

    sealed class RegistrationEvent {
        object OpenHome : RegistrationEvent()
    }

    companion object {
        const val ERROR_CODE_ALREADY_EXIST_USER = 1555
    }
}

