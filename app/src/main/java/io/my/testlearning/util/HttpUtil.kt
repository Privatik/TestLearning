package io.my.testlearning.util

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.my.testlearning.data.remote.NetworkError
import java.lang.Exception

suspend inline fun <reified T> HttpClient.requestAsResult(
    urlString: String,
    method: HttpMethod,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = request<T> {
            url.takeFrom(urlString)
            this.method = method
            block()
        }
        Result.success(response)
    }  catch (e: ResponseException){
        when (e.response.status.value){
            401 -> Result.failure(NetworkError.AuthFail)
            403 -> Result.failure(NetworkError.ForbiddenFail)
            else -> Result.failure(NetworkError.GlobalFail(e))
        }
    } catch (e: Exception){
        Result.failure(NetworkError.GlobalFail(e))
    }
}

suspend inline fun <reified T> HttpClient.getAsResult(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit
): Result<T> = requestAsResult(
    urlString = urlString,
    method = HttpMethod.Get,
    block = block
)

suspend inline fun <reified T> HttpClient.postAsResult(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit
): Result<T> = requestAsResult(
    urlString = urlString,
    method = HttpMethod.Post,
    block = block
)
