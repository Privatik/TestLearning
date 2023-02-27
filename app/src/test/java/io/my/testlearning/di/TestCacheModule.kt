package io.my.testlearning.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dagger.Module
import dagger.Provides
import io.my.testlearning.Database
import io.my.testlearning.data.cache.ShoppingDao
import io.my.testlearning.data.cache.impl
import javax.inject.Singleton

@Module
class TestCacheModule {

    @Singleton
    @Provides
    fun provideDatabase(): Database {
        val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            Database.Schema.create(this)
        }
        return Database(inMemorySqlDriver)
    }

    @Provides
    fun provideShoppingDao(
        database: Database
    ): ShoppingDao = impl(database.shopEntityQueries)
}