package io.my.testlearning.di

import dagger.Binds
import dagger.Module
import io.my.testlearning.data.ShoppingRepository
import io.my.testlearning.data.ShoppingRepositoryImpl
import io.my.testlearning.data.cache.ShoppingDao
import io.my.testlearning.data.dto.ShoppingItemDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Module
interface RepositoryModule {

    @Binds
    fun bindShoppingRepository(repositoryImpl: ShoppingRepositoryImpl): ShoppingRepository
}