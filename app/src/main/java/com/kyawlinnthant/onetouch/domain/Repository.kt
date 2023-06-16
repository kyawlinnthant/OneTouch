package com.kyawlinnthant.onetouch.domain

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import kotlinx.coroutines.flow.Flow

typealias SignUpWithEmailResponse = DataResult<Boolean>
typealias SignInWithEmailResponse = DataResult<Boolean>
typealias SignInWithCredentialResponse = DataResult<Boolean>
typealias SignOutResponse = DataResult<Boolean>

interface Repository {

    suspend fun getAuthenticated(): Boolean

    suspend fun getCurrentUser(): Flow<CurrentUser>

    suspend fun signupWithEmail(email: String, pwd: String): SignUpWithEmailResponse
    suspend fun signInWithEmail(email: String, pwd: String): SignInWithEmailResponse
    suspend fun signInWithCredential(credential: AuthCredential): SignInWithCredentialResponse
    suspend fun signOut(): SignOutResponse
    suspend fun getSignInResult(): DataResult<BeginSignInResult>

    suspend fun getSignInCredential(intent: Intent?): DataResult<SignInCredential>

}