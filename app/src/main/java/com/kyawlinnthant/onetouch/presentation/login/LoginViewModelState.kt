package com.kyawlinnthant.onetouch.presentation.login

data class LoginViewModelState(
    val isLoading: Boolean = false,
    val googleLoading: Boolean = false,
    val googleEnabled: Boolean = true,
    val errorEmail: Boolean = false,
    val errorPwd: Boolean = false
) {
    fun loading() = isLoading
    fun errorEmail() = errorEmail
    fun errorPwd() = errorPwd
    fun loadingGoogle() = googleLoading
    fun enabledGoogle() = googleEnabled
}
