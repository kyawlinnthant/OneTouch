package com.kyawlinnthant.onetouch.data

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.kyawlinnthant.onetouch.common.Constant
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.DataStoreSource
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.firebase.FirebaseModule
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val oneTapClient: SignInClient,
    @FirebaseModule.SignInRequest private val signInRequest: BeginSignInRequest,
    @FirebaseModule.SignupRequest private val signupRequest: BeginSignInRequest,
    private val fireStore: FirebaseFirestore,
    private val datasource : DataStoreSource,

) : Repository {
    override val isAuthenticated: Boolean
        get() = firebaseAuth.currentUser != null

    override suspend fun oneTapSignIn(): DataResult<BeginSignInResult> {
        return try {
            val sighInResult = oneTapClient.beginSignIn(signInRequest).await()
            DataResult.Success(sighInResult)
        } catch (e: Exception) {
            try {
                val signupResult = oneTapClient.beginSignIn(signupRequest).await()
                DataResult.Success(signupResult)
            } catch (e: Exception) {
                DataResult.Fail(e.localizedMessage ?: "Something's Wrong!")
            }
        }
    }

    override suspend fun firebaseSignIn(credential: AuthCredential): DataResult<Boolean> {
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            val isNew = result.additionalUserInfo?.isNewUser ?: false
            if (isNew) {
                addUserToFireStore()
            }
            DataResult.Success(true)
        } catch (e: Exception) {
            DataResult.Fail(e.localizedMessage ?: "Something's Wrong!")
        }
    }

    override suspend fun getAuthenticated(): Boolean {
        return datasource.pullAuthenticated().firstOrNull()?: false
    }

    private suspend fun addUserToFireStore() {
        firebaseAuth.currentUser?.apply {
            val user = mapOf(
                Constant.NAME to displayName,
                Constant.EMAIL to email,
                Constant.PHOTO to photoUrl?.toString(),
                Constant.CREATED to serverTimestamp()
            )
            fireStore.collection(Constant.USERS).document(uid).set(user).await()
        }
    }
}