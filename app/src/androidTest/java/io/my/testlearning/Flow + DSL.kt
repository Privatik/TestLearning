package io.my.testlearning

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


@Suppress("ControlFlowWithEmptyBody")
suspend fun <T> Flow<T>.awaitTerminateFlow(){
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        if (exception !is AssertionError) throw exception
    }

    supervisorScope {
        launch(coroutineExceptionHandler) {
            test {
                while (true){
                    val event = awaitEvent()
                    if (event.isTerminal) break
                }
            }
        }
    }
}