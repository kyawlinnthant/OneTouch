package com.kyawlinnthant.onetouch.data.ds

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    suspend fun putAuthenticated(isAuthenticated: Boolean)
    suspend fun pullAuthenticated(): Flow<Boolean>
    suspend fun putId(id: String)
    suspend fun pullId(): Flow<String>
    suspend fun putName(name: String)
    suspend fun pullName(): Flow<String>
    suspend fun putEmail(email: String)
    suspend fun pullEmail(): Flow<String>
    suspend fun putPhoto(url: String)
    suspend fun pullPhoto(): Flow<String>
    suspend fun putCreated(timestamp: Long)
    suspend fun pullCreated(): Flow<Long>
}