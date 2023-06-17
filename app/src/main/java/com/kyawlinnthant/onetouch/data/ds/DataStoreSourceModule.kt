package com.kyawlinnthant.onetouch.data.ds

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreSourceModule {

    @Binds
    @Singleton
    fun bindDataStoreSource(source: DataStoreSourceImpl): DataStoreSource
}
