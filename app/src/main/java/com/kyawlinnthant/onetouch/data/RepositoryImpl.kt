package com.kyawlinnthant.onetouch.data

import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import com.kyawlinnthant.onetouch.data.ds.DataStoreSource
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.firebase.FirebaseSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebaseSource: FirebaseSource,
    private val datastoreSource: DataStoreSource,
) : Repository {

    override suspend fun getAuthenticated(): Boolean {
        return datastoreSource.pullAuthenticated().firstOrNull() ?: false
    }

    override suspend fun getCurrentUser(): Flow<CurrentUser> {
        return firebaseSource.getCurrentUser()
    }

    override suspend fun signupWithEmail(email: String, pwd: String): DataResult<Boolean> {
        return when (val result = firebaseSource.signupWithEmail(email = email, pwd = pwd)) {
            is DataResult.Fail -> DataResult.Fail(result.message)
            is DataResult.Success -> {
                val isSuccessful = result.data != CurrentUser()
                //save in datastore
                datastoreSource.putAuthenticated(isSuccessful)
                DataResult.Success(isSuccessful)
            }
        }
    }

    override suspend fun signInWithEmail(email: String, pwd: String): DataResult<Boolean> {
        return when (val result = firebaseSource.signInWithEmail(email = email, pwd = pwd)) {
            is DataResult.Fail -> DataResult.Fail(result.message)
            is DataResult.Success -> {
                val isSuccessful = result.data != CurrentUser()
                //save in datastore
                datastoreSource.putAuthenticated(isSuccessful)
                DataResult.Success(isSuccessful)
            }
        }
    }

}