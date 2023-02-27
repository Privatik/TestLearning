@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.testlearning.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class TestCoroutineModule {

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineContext = UnconfinedTestDispatcher()

}