package io.my.testlearning.util

import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class PresenterKey(val value: KClass<out Presenter<*, *, *>>)

class DefaultPresenterFactory @Inject constructor(
    private val presenterFactories: Map<Class<out Presenter<*, *, *>>, @JvmSuppressWildcards Provider<Presenter<*, *, *>>>
): PresenterFactory {

    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        @Suppress("UNCHECKED_CAST")
        return presenterFactories.getValue(model as Class<out Presenter<*, *, *>>).get() as P
    }

}