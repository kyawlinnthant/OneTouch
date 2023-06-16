package com.kyawlinnthant.onetouch.presentation.register

data class RegisterViewModelState(
    val isLoading : Boolean = false
){
    fun loading() = isLoading
}
