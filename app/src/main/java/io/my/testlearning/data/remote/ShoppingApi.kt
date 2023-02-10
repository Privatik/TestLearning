package io.my.testlearning.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.my.testlearning.BASE_API
import io.my.testlearning.BuildConfig
import io.my.testlearning.GET_IMAGE
import io.my.testlearning.ShopEntityQueries
import io.my.testlearning.data.remote.model.ImageResponse
import io.my.testlearning.util.getAsResult

fun impl(client: HttpClient):ShoppingApi{
    return ShoppingApiImpl(client)
}

interface ShoppingApi {

    suspend fun searchImagesByQuery(
        query: String
    ): Result<ImageResponse>
}

private class ShoppingApiImpl(
    private val client: HttpClient
): ShoppingApi {

    override suspend fun searchImagesByQuery(
        query: String,
    ): Result<ImageResponse> =
        client.getAsResult<ImageResponse>(
            urlString = "$BASE_API$GET_IMAGE"
        ) {
            url.parameters.apply {
                append("q",query)
                append("key", BuildConfig.API_KEY)
            }
        }
}