package com.kyawlinnthant.onetouch.presentation.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.common.FormValidator
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.navigation.AppNavigator
import com.kyawlinnthant.onetouch.presentation.main.Graph
import com.kyawlinnthant.onetouch.presentation.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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

    private val vmEvent = MutableSharedFlow<LoginEvent>()
    val uiEvent get() = vmEvent.asSharedFlow()

    private val vmState = MutableStateFlow(LoginViewModelState())
    val isLoading = vmState
        .map(LoginViewModelState::loading)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.loading()
        )
    val isErrorEmail = vmState
        .map(LoginViewModelState::errorEmail)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.errorEmail()
        )
    val isErrorPwd = vmState
        .map(LoginViewModelState::errorPwd)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.errorPwd()
        )
    val googleLoading = vmState
        .map(LoginViewModelState::loadingGoogle)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.loadingGoogle()
        )
    val googleEnabled = vmState
        .map(LoginViewModelState::enabledGoogle)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.enabledGoogle()
        )

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.GoForgotPassword -> navigator.to(Screen.ForgotPassword.name)
            LoginAction.GoRegister -> navigator.to(Screen.Register.name)
            LoginAction.GoogleLogin -> loginWithGoogleAccount()
            is LoginAction.Login -> loginWithEmail(email = action.email, pwd = action.pwd)
        }
    }

    private fun validateLogin(email: String, pwd: String): Boolean {
        val isValidatedEmail = FormValidator.isVerifiedEmail(email.trim())
        val isValidatedPwd = FormValidator.isVerifiedPassword(pwd.trim())

        vmState.update {
            it.copy(
                errorEmail = !isValidatedEmail,
                errorPwd = !isValidatedPwd
            )
        }
        return isValidatedEmail && isValidatedPwd
    }

    private fun loginWithEmail(email: String, pwd: String) {
        if (validateLogin(email = email, pwd = pwd)) {
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
                            vmEvent.emit(LoginEvent.Snack(it.message))
                        }

                        is DataResult.Success -> {
                            navigator.to(
                                route = Graph.Feature.route,
                                popupToRoute = Graph.Auth.route,
                                inclusive = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loginWithGoogleAccount() {
        vmState.update {
            it.copy(
                googleLoading = true,
                googleEnabled = false,
            )
        }
        viewModelScope.launch {
            repo.getSignInResult().also {
                when (it) {
                    is DataResult.Fail -> {
                        vmState.update { state ->
                            state.copy(
                                googleLoading = false,
                                googleEnabled = true,
                            )
                        }
                        vmEvent.emit(LoginEvent.Snack(it.message))
                    }

                    is DataResult.Success -> vmEvent.emit(LoginEvent.LaunchGoogleAccounts(it.data))
                }
            }
        }
    }

    fun updateGoogleLoading(status: Boolean) {
        vmState.update {
            it.copy(
                googleLoading = status,
                googleEnabled = !status
            )
        }
    }


    fun getSignInCredential(intent: Intent?) {
        viewModelScope.launch {
            repo.getSignInCredential(intent).also {
                when (it) {
                    is DataResult.Fail -> vmEvent.emit(LoginEvent.Snack(it.message))

                    is DataResult.Success -> {
                        vmState.update { state ->
                            state.copy(isLoading = true)
                        }
                        val credentials = it.data
                        val googleIdToken = credentials.googleIdToken
                        val googleCredentials = GoogleAuthProvider.getCredential(
                            googleIdToken,
                            null
                        )
                        repo.signInWithCredential(googleCredentials).also { result ->
                            vmState.update { state ->
                                state.copy(isLoading = false)
                            }
                            when (result) {
                                is DataResult.Fail -> {
                                    vmEvent.emit(LoginEvent.Snack(result.message))
                                }

                                is DataResult.Success -> {
                                    navigator.to(
                                        route = Graph.Feature.route,
                                        popupToRoute = Graph.Auth.route,
                                        inclusive = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}