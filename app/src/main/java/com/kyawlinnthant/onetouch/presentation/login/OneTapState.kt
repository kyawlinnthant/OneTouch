package com.kyawlinnthant.onetouch.presentation.login

import com.google.android.gms.auth.api.identity.BeginSignInResult

sealed interface OneTapState{
    object Loading : OneTapState
    data class Error(val message : String) : OneTapState
    data class Success(val data : BeginSignInResult) : OneTapState
}