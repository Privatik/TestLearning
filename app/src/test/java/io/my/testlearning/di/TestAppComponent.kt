package io.my.testlearning.di

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        TestCacheModule::class,
        TestRemoteModule::class,
        TestCoroutineModule::class,
        RepositoryModule::class,
        PresenterModule::class
    ]
)
@Singleton
interface TestAppComponent: AppComponent {

}