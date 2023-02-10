package io.my.testlearning

import android.app.Application
import android.content.Context
import com.io.navigation_common.PresenterFactory
import io.my.testlearning.data.cache.ShoppingDao
import io.my.testlearning.data.cache.ShoppingDatabase
import io.my.testlearning.data.cache.impl
import io.my.testlearning.di.AppComponent
import io.my.testlearning.di.DaggerAppComponent
import kotlin.properties.Delegates

class App: Application() {

    private var component: AppComponent by Delegates.notNull()

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }

    fun factory(): PresenterFactory = component.factory()

}

fun Context.presenterFactory(): PresenterFactory{
    val context = if (this is App) this else applicationContext as App
    return context.factory()
}