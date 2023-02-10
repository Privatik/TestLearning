package io.my.testlearning.di

import com.io.navigation_common.PresenterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.my.testlearning.ui.presenter.ShoppingPresenter
import io.my.testlearning.util.DefaultPresenterFactory
import io.my.testlearning.util.Presenter
import io.my.testlearning.util.PresenterKey
import javax.inject.Singleton

@Module
interface PresenterModule {

    @Binds
    @Singleton
    fun bindPresenter(factory: DefaultPresenterFactory): PresenterFactory

    @Binds
    @[IntoMap PresenterKey(ShoppingPresenter::class)]
    fun bindProfilePresenter(presenter: ShoppingPresenter): Presenter<*, *, *>
}