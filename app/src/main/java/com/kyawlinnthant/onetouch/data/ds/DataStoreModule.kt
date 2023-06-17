package com.kyawlinnthant.onetouch.data.ds

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.kyawlinnthant.onetouch.data.ds.DataStoreSourceImpl.Companion.PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile(PREF_NAME)
        }
    )
}
