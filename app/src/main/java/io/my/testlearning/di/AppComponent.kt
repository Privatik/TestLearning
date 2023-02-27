package io.my.testlearning.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.my.testlearning.util.DefaultPresenterFactory
import javax.inject.Singleton

@Component(
    modules = [
        CacheModule::class,
        RemoteModule::class,
        RemoteModule::class,
        RepositoryModule::class,
        PresenterModule::class,
        CoroutineModule::class
    ]
)
@Singleton
interface AppComponent {

    fun factory(): DefaultPresenterFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

}