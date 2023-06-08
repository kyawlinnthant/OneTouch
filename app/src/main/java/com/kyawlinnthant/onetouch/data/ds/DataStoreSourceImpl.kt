package com.kyawlinnthant.onetouch.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kyawlinnthant.onetouch.common.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class DataStoreSourceImpl @Inject constructor(
    private val store: DataStore<Preferences>,
    @DispatcherModule.IO private val io: CoroutineDispatcher
) : DataStoreSource {

    companion object {
        const val PREF_NAME = "ds.pref"
        val IS_AUTHENTICATED = booleanPreferencesKey("ds.pref.authenticated")
        val NAME = stringPreferencesKey("ds.pref.name")
        val EMAIL = stringPreferencesKey("ds.pref.email")
        val PHOTO = stringPreferencesKey("ds.pref.photo")
        val CREATED = longPreferencesKey("ds.pref.created")
    }

    override suspend fun putAuthenticated(isAuthenticated: Boolean) {
        withContext(io) {
            store.edit {
                it[IS_AUTHENTICATED] = isAuthenticated
            }
        }
    }

    override suspend fun pullAuthenticated(): Flow<Boolean> {
        return store.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e

            }.map {
                it[IS_AUTHENTICATED] ?: false
            }.flowOn(io)
    }

    override suspend fun putName(name: String) {
        withContext(io) {
            store.edit {
                it[NAME] = name
            }
        }
    }

    override suspend fun pullName(): Flow<String> {
        return store.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e

            }.map {
                it[NAME] ?: ""
            }.flowOn(io)
    }

    override suspend fun putEmail(email: String) {
        withContext(io) {
            store.edit {
                it[EMAIL] = email
            }
        }
    }

    override suspend fun pullEmail(): Flow<String> {
        return store.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e

            }.map {
                it[EMAIL] ?: ""
            }.flowOn(io)
    }

    override suspend fun putPhoto(url: String) {
        withContext(io) {
            store.edit {
                it[PHOTO] = url
            }
        }
    }

    override suspend fun pullPhoto(): Flow<String> {
        return store.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e

            }.map {
                it[PHOTO] ?: ""
            }.flowOn(io)
    }

    override suspend fun putCreated(timestamp: Long) {
        withContext(io) {
            store.edit {
                it[CREATED] = timestamp
            }
        }
    }

    override suspend fun pullCreated(): Flow<Long> {
        return store.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e

            }.map {
                it[CREATED] ?: 0L
            }.flowOn(io)
    }
}