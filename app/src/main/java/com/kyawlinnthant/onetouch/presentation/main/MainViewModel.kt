package com.kyawlinnthant.onetouch.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.onetouch.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private val vmLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn get() = vmLoggedIn.asStateFlow()

    init {
        getLoggedIn()
    }

    private fun getLoggedIn() {
        viewModelScope.launch {
            vmLoggedIn.value = repo.getAuthenticated()
        }
    }

}