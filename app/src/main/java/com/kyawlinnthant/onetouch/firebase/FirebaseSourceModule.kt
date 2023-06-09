package com.kyawlinnthant.onetouch.firebase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseSourceModule {
    @Binds
    @Singleton
    fun bindFirebaseSource(source: FirebaseSourceImpl): FirebaseSource
}