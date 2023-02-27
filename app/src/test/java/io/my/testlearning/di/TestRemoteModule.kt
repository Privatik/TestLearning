package io.my.testlearning.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.my.testlearning.data.remote.ShoppingApi
import io.my.testlearning.data.remote.model.ImageResponse
import io.my.testlearning.data.remote.model.ImageResult
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import javax.inject.Singleton

@Module
class TestRemoteModule {

    @Provides
    @Singleton
    fun provideShoppingApi(): ShoppingApi {
        val result1 = Result.success(
            ImageResponse(
                hits = emptyList(),
            )
        )

        val result2 = Result.success(
            ImageResponse(
                hits = listOf(ImageResult("http://logo.png")),
            )
        )

        val api = mock<ShoppingApi> {
            onBlocking { searchImagesByQuery("") } doReturn result1
            onBlocking { searchImagesByQuery("logo") } doReturn result2
        }

        return api
    }
}