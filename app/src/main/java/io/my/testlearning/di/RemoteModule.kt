package io.my.testlearning.di

import android.util.Log
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.my.testlearning.data.remote.ShoppingApi
import io.my.testlearning.data.remote.impl
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            expectSuccess = true

            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Logger Ktor =>", message)
                    }

                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                if (!(body is FormDataContent || body is MultiPartFormDataContent)) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }
        }

    @Provides
    fun provideShoppingApi(
        client: HttpClient
    ): ShoppingApi = impl(client)
}