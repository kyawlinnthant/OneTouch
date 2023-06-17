package com.kyawlinnthant.onetouch.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class DEFAULT

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IO

    @Provides
    @DEFAULT
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IO
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
