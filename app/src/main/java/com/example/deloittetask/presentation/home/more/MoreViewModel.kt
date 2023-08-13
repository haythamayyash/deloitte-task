package com.example.deloittetask.presentation.home.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.data.model.Articles
import com.example.deloittetask.domain.usecase.GetArticlesUseCase
import com.example.deloittetask.util.DeloitteError
import com.example.deloittetask.util.isConnectionException
import com.example.deloittetask.util.mapToDeloitteError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(private val getArticlesUseCase: GetArticlesUseCase) :
    BaseViewModel() {



    private val _viewState = MutableLiveData<MoreViewState>()
    val viewState: LiveData<MoreViewState> = _viewState

    init {
        getArticles()
    }

    private fun getArticles() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if(throwable.isConnectionException)
            _viewState.value = MoreViewState.Failure(throwable.mapToDeloitteError())
        }

        viewModelScope.launch(exceptionHandler) {
            _viewState.value = MoreViewState.Loading
            val articles = withContext(Dispatchers.IO) {
                getArticlesUseCase.execute()
            }
            _viewState.value = MoreViewState.Success(articles)
        }
    }

    sealed class MoreViewState {
        object Loading : MoreViewState()
        data class Success(val articles: List<Articles.Article?>) : MoreViewState()
        data class Failure(val deloitteError: DeloitteError) : MoreViewState()
    }
}