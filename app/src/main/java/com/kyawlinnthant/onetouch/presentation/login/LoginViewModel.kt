package com.kyawlinnthant.onetouch.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.navigation.AppNavigator
import com.kyawlinnthant.onetouch.presentation.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository,
    private val navigator: AppNavigator,
) : ViewModel() {

    private val vmState = MutableStateFlow(LoginViewModelState())
    val isLoading = vmState
        .map(LoginViewModelState::loading)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.loading()
        )

    private fun login(email: String, pwd: String) {
        vmState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            repo.signInWithEmail(
                email = email,
                pwd = pwd
            ).also {
                vmState.update { state ->
                    state.copy(isLoading = true)
                }
                when (it) {
                    is DataResult.Fail -> {

                    }

                    is DataResult.Success -> {
                        //navigate to home
                    }
                }
            }
        }
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.GoForgotPassword -> navigator.to(Screen.ForgotPassword.name)
            LoginAction.GoRegister -> navigator.to(Screen.Register.name)
            is LoginAction.Login -> {
                login(
                    email = action.email,
                    pwd = action.pwd
                )
            }
        }
    }


}