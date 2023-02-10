package io.my.testlearning.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class ActionIntent(protected val coroutineScope: CoroutineScope)

fun getEmptyIntent(): ActionIntent {
    fun getScope(): CoroutineScope {
        @Suppress("UNREACHABLE_CODE")
        return throw UnsupportedOperationException()
    }

    class EmptyIntent(): ActionIntent(getScope())

    return EmptyIntent()
}

interface Intent<P: Any>{
    fun asFlow(): Flow<P>
}

class IntentWithOutParams(
    private val tag: String,
    private val coroutineScope: CoroutineScope,
): Intent<Unit> {
    private val intentWithParams: IntentWithParams<Unit> = IntentWithParams(tag, coroutineScope)

    override fun asFlow() = intentWithParams.asFlow()

    operator fun invoke() = intentWithParams(Unit)

}

class IntentWithParams<P: Any>(
    private val tag: String,
    private val coroutineScope: CoroutineScope
): Intent<P> {
    private val _data = MutableSharedFlow<P>()

    override fun asFlow(): Flow<P> = _data.asSharedFlow()

    operator fun invoke(params: P) {
        coroutineScope.launch{
//            Log.d("DoActionIntent","intent $tag")
            _data.emit(params)
        }
    }
}

fun <P: Any> ActionIntent.createIntent(tag: String, coroutineScope: CoroutineScope): IntentWithParams<P>{
    return IntentWithParams<P>(tag, coroutineScope)
}

fun ActionIntent.createIntentWithoutParams(tag: String, coroutineScope: CoroutineScope): IntentWithOutParams{
    return IntentWithOutParams(tag, coroutineScope)
}