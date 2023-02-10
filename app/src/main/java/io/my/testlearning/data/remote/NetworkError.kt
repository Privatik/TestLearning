package io.my.testlearning.data.remote

sealed class NetworkError(
    override val message: String?
): Throwable(message){
    object AuthFail: NetworkError(message = "Error 401")
    object ForbiddenFail: NetworkError(message = "Error 403")
    class GlobalFail(throwable: Throwable): NetworkError(throwable.message)
}