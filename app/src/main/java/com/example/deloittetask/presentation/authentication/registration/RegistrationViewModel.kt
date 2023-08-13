package com.example.deloittetask.presentation.authentication.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.usecase.RegisterUseCase
import com.example.deloittetask.util.DeloitteError
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


    fun register(user: User) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _viewState.value = RegistrationViewState.Failure(throwable.mapToDeloitteError())
        }

        viewModelScope.launch(exceptionHandler) {
            _viewState.value = RegistrationViewState.Loading
            withContext(Dispatchers.IO) {
                registerUseCase.execute(user)
            }
            _viewState.value = RegistrationViewState.Success
        }
    }

    sealed class RegistrationViewState {
        object Loading : RegistrationViewState()
        object Success : RegistrationViewState()
        data class Failure(val deloitteError: DeloitteError) : RegistrationViewState()
    }
}

