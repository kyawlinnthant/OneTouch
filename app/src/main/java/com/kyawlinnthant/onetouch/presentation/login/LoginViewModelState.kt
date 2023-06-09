package com.kyawlinnthant.onetouch.presentation.login

data class LoginViewModelState(
    val isLoading : Boolean = false
){
    fun loading() = isLoading
}
