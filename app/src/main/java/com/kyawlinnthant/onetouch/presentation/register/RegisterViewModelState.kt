package com.kyawlinnthant.onetouch.presentation.register

import android.net.Uri

data class RegisterViewModelState(
    val isLoading: Boolean = false,
    val tmpUri: Uri = Uri.EMPTY,
    val errorName: Boolean = false,
    val errorEmail: Boolean = false,
    val errorPwd: Boolean = false,
    val errorConfirmPwd: Boolean = false
) {
    fun loading() = isLoading
    fun tmpUri() = tmpUri
}
