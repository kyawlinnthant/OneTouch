package com.kyawlinnthant.onetouch.data

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import com.kyawlinnthant.onetouch.data.ds.DataStoreSource
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.domain.SignInWithCredentialResponse
import com.kyawlinnthant.onetouch.domain.SignInWithEmailResponse
import com.kyawlinnthant.onetouch.domain.SignOutResponse
import com.kyawlinnthant.onetouch.domain.SignUpWithEmailResponse
import com.kyawlinnthant.onetouch.firebase.FirebaseSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class RepositoryImpl @Inject constructor(
    private val firebaseSource: FirebaseSource,
    private val datastoreSource: DataStoreSource
) : Repository {

    override suspend fun getAuthenticated(): Boolean {
        return datastoreSource.pullAuthenticated().firstOrNull() ?: false
    }

    override suspend fun getCurrentUser(): Flow<CurrentUser> {
        return firebaseSource.getCurrentUser()
    }

    override suspend fun signupWithEmail(email: String, pwd: String): SignUpWithEmailResponse {
        return when (val result = firebaseSource.signupWithEmail(email = email, pwd = pwd)) {
            is DataResult.Fail -> DataResult.Fail(result.message)
            is DataResult.Success -> {
                val isSuccessful = result.data != CurrentUser()
                // save in datastore
                datastoreSource.putAuthenticated(isSuccessful)
                DataResult.Success(isSuccessful)
            }
        }
    }

    override suspend fun signInWithEmail(email: String, pwd: String): SignInWithEmailResponse {
        return when (val result = firebaseSource.signInWithEmail(email = email, pwd = pwd)) {
            is DataResult.Fail -> DataResult.Fail(result.message)
            is DataResult.Success -> {
                val isSuccessful = result.data != CurrentUser()
                // save in datastore
                datastoreSource.putAuthenticated(isSuccessful)
                DataResult.Success(isSuccessful)
            }
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential): SignInWithCredentialResponse {
        return when (val result = firebaseSource.signInWithCredential(credential)) {
            is DataResult.Fail -> DataResult.Fail(result.message)
            is DataResult.Success -> {
                val isSuccessful = result.data != CurrentUser()
                datastoreSource.putAuthenticated(isSuccessful)
                DataResult.Success(isSuccessful)
            }
        }
    }

    override suspend fun signOut(): SignOutResponse {
        val result = firebaseSource.logout()
        result.data?.let {
            if (it) datastoreSource.putAuthenticated(false)
        }
        return result
    }

    override suspend fun getSignInResult(): DataResult<BeginSignInResult> {
        return firebaseSource.oneTapSignIn()
    }

    override suspend fun getSignInCredential(intent: Intent?): DataResult<SignInCredential> {
        return firebaseSource.getSignInCredential(intent)
    }
}
