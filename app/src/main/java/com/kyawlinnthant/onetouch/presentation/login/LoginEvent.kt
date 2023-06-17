package com.kyawlinnthant.onetouch.presentation.login

import com.google.android.gms.auth.api.identity.BeginSignInResult

sealed interface LoginEvent {
    data class LaunchGoogleAccounts(val result: BeginSignInResult) : LoginEvent
    data class Snack(val message: String) : LoginEvent
}
