package io.my.testlearning.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class CoroutineModule {

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineContext = Dispatchers.IO

}