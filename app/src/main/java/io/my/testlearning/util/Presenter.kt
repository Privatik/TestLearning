package io.my.testlearning.util

import com.example.machine.ReducerDSL
import com.example.machine.reducer
import com.io.navigation.AndroidPresenter
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*

class BasePresenterAdapter(): PresenterStoreOwner.Adapter<String>(){

    override fun getGuide(): String = "1111"

    override fun getCacheKey(currentGuide: String): String = "2222"

}

abstract class Presenter<S: Any, I: ActionIntent, E: Any>(
    initialState: S
): AndroidPresenter() {

    private val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    protected abstract fun CoroutineScope.buildIntent(): I

    val intent: I by lazy(LazyThreadSafetyMode.NONE) { presenterScope.buildIntent() }

    private val _state = MutableStateFlow<S>(initialState)
    val state: StateFlow<S> by lazy(LazyThreadSafetyMode.NONE) {
        buildReducer(_state.value)
        _state.asStateFlow()
    }

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    protected abstract fun ReducerDSL<S, E>.reducer()

    private fun buildReducer(state: S){
        reducer<S, E>(state){ reducer() }.apply {
            this.state
                .onEach {
                    _state.emit(it)
                }.launchIn(presenterScope)

            effects
                .onEach {
                    _singleEffect.emit(it)
                }.launchIn(presenterScope)
        }
    }

    final override fun clear() {
        presenterScope.cancel()
    }

}