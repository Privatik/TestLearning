package io.my.testlearning.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import io.my.testlearning.Database
import io.my.testlearning.ShopEntityQueries
import io.my.testlearning.data.cache.ShoppingDao
import io.my.testlearning.data.cache.impl
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ): Database {
        return AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "shopping.db"
        ).let { Database(it) }
    }

    @Provides
    fun provideShoppingDao(
        database: Database
    ): ShoppingDao = impl(database.shopEntityQueries)



}