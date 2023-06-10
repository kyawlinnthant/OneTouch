package com.kyawlinnthant.onetouch.firebase

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import kotlinx.coroutines.flow.Flow

interface FirebaseSource {
    suspend fun signupWithEmail(email: String, pwd: String): DataResult<CurrentUser>
    suspend fun signInWithEmail(email: String, pwd: String): DataResult<CurrentUser>

    suspend fun signInWithCredential(credential: AuthCredential) : DataResult<CurrentUser>

    suspend fun getCurrentUser() : Flow<CurrentUser>

    suspend fun oneTapSignIn(): DataResult<BeginSignInResult>

    suspend fun getSignInCredential(intent : Intent?) : DataResult<SignInCredential>

    suspend fun logout() : DataResult<Boolean>

}