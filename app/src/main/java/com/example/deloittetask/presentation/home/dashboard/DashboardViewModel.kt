package com.example.deloittetask.presentation.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.usecase.GetUserDataUseCase
import com.example.deloittetask.domain.usecase.GetUserNationalIdUseCase
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.mapToDeloitteError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserNationalIdUseCase: GetUserNationalIdUseCase
) :
    BaseViewModel() {

    private val _viewState = MutableLiveData<DashboardViewState>()
    val viewState: LiveData<DashboardViewState> = _viewState

    init {
        getUserData()
    }

    fun getUserData() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _viewState.value = DashboardViewState.Failure(throwable.mapToDeloitteError())
        }

        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                _viewState.value = DashboardViewState.Loading
                val user = withContext(Dispatchers.IO) {
                    getUserDataUseCase.execute(getUserNationalIdUseCase.execute() ?: 0)
                }
                _viewState.value = DashboardViewState.Success(user!!)
            }
        }
    }

    sealed class DashboardViewState {
        object Loading : DashboardViewState()
        data class Success(val user: User) : DashboardViewState()
        data class Failure(val deloitteError: DeloitteError) : DashboardViewState()
    }
}

