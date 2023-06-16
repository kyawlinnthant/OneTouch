package com.kyawlinnthant.onetouch.data

import com.kyawlinnthant.onetouch.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthRepoModule {
    @Binds
    @Singleton
    fun bindAuthRepo(repo: RepositoryImpl): Repository
}