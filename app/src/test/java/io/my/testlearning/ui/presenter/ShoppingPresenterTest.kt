@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.testlearning.ui.presenter

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.my.testlearning.MainCoroutineRule
import io.my.testlearning.ShopEntity
import io.my.testlearning.data.cache.FakeShoppingRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.Delegates

class ShoppingPresenterTest{

    private val shopItems = listOf(
        ShopEntity(1,"Bananas", 2, 0.5, null),
        ShopEntity(2,"Bananas", 4, 0.5, null),
        ShopEntity(3,"Bananas", 6, 0.5, null),
    )
    private var presenter: ShoppingPresenter by Delegates.notNull()
    private var state: StateFlow<ShoppingState> by Delegates.notNull()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp(){
        presenter = ShoppingPresenter(FakeShoppingRepositoryImpl(mainDispatcherRule.dispatcher))
        state = presenter.state
    }

    @Test
    fun totalPrice_return6() = runTest {
        shopItems.forEach {
            it.addItem()
        }

        assertThat(state.value.totalPrice).isEqualTo(6.0)
    }

    @Test
    fun totalPrice_return0() = runTest {
        shopItems.forEach {
            it.addItem()
        }

        shopItems.forEach {
            presenter.intent.removeItem(it.id)
        }

        assertThat(state.value.totalPrice).isEqualTo(0.0)
    }

    private fun ShopEntity.addItem(){
        presenter.intent.addItem(
            ShoppingIntent.ShoppingItemInsert(
                name, amount, price
            )
        )
    }
}