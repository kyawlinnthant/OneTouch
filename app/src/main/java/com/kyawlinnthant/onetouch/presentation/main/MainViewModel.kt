package com.kyawlinnthant.onetouch.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val vmLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn get() = vmLoggedIn.asStateFlow()

    val instructor = appNavigator.instructor

    init {
        getLoggedIn()
    }

    private fun getLoggedIn() {
        viewModelScope.launch {
            vmLoggedIn.value = repo.getAuthenticated()
        }
    }
}
