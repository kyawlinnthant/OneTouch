package com.kyawlinnthant.onetouch.firebase

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.kyawlinnthant.onetouch.common.Constant
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val oneTapClient: SignInClient,
    @FirebaseModule.SignInRequest private val signInRequest: BeginSignInRequest,
    @FirebaseModule.SignupRequest private val signupRequest: BeginSignInRequest,
    private val fireStore: FirebaseFirestore,
) : FirebaseSource {

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
            if (isNew && firebaseAuth.currentUser != null) {
                addUserToFireStore(user = firebaseAuth.currentUser!!)
            }
            DataResult.Success(true)
        } catch (e: Exception) {
            DataResult.Fail(e.localizedMessage ?: "Something's Wrong!")
        }
    }

    override suspend fun signupWithEmail(email: String, pwd: String): DataResult<CurrentUser> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, pwd).await()
            val currentUser = firebaseAuth.currentUser?.asUser() ?: CurrentUser()
            DataResult.Success(currentUser)
        } catch (e: Exception) {
            DataResult.Fail(e.localizedMessage ?: "Something's wrong!")
        }
    }

    override suspend fun signInWithEmail(email: String, pwd: String): DataResult<CurrentUser> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, pwd).await()
            val currentUser = firebaseAuth.currentUser?.asUser() ?: CurrentUser()
            DataResult.Success(currentUser)
        } catch (e: Exception) {
            DataResult.Fail(e.localizedMessage ?: "Something's wrong!")
        }
    }

    override suspend fun getCurrentUser(): Flow<CurrentUser> {
        val user = firebaseAuth.currentUser?.asUser() ?: CurrentUser()
        return flow { emit(user) }
    }

    private suspend fun addUserToFireStore(user: FirebaseUser) {
        user.apply {
            val fireStoreUser = mapOf(
                Constant.NAME to displayName,
                Constant.EMAIL to email,
                Constant.PHOTO to photoUrl?.toString(),
                Constant.CREATED to serverTimestamp()
            )
            fireStore.collection(Constant.USERS).document(uid).set(fireStoreUser).await()
        }
    }

    private suspend fun getUsersFromFireStore() {
        val users = fireStore.collection(Constant.USERS).get().await()
    }
}