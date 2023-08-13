package com.example.deloittetask.presentation.home.more

import androidx.lifecycle.viewModelScope
import com.example.deloittetask.BaseViewModel
import com.example.deloittetask.domain.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(private val getArticlesUseCase: GetArticlesUseCase) :
    BaseViewModel() {

    fun getArticles() = viewModelScope.launch {
        val articles  = getArticlesUseCase.execute()
    }
}