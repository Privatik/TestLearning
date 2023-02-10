package io.my.testlearning.data.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.my.testlearning.Database

class ShoppingDatabase(
    context: Context
) {
    private val driver = AndroidSqliteDriver(
        schema = Database.Schema,
        context = context,
        name = "shopping.db"
    )

    private val database = Database(driver)

    fun getShopEntityQueries() = database.shopEntityQueries
}