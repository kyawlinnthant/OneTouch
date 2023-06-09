package com.kyawlinnthant.onetouch.domain

import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAuthenticated(): Boolean

    suspend fun getCurrentUser(): Flow<CurrentUser>

    suspend fun signupWithEmail(email: String, pwd: String): DataResult<Boolean>
    suspend fun signInWithEmail(email: String, pwd: String): DataResult<Boolean>
}