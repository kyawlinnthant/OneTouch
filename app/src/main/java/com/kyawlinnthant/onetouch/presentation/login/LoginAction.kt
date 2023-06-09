package com.kyawlinnthant.onetouch.presentation.login

sealed interface LoginAction {
    object GoRegister : LoginAction
    object GoForgotPassword : LoginAction
    data class Login(val email: String, val pwd: String) : LoginAction
}