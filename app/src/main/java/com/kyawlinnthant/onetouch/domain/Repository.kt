package com.kyawlinnthant.onetouch.domain

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.kyawlinnthant.onetouch.common.DataResult

interface Repository {
    val isAuthenticated: Boolean
    suspend fun oneTapSignIn(): DataResult<BeginSignInResult>
    suspend fun firebaseSignIn(credential : AuthCredential): DataResult<Boolean>

    suspend fun getAuthenticated() : Boolean
}