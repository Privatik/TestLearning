@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.testlearning

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.io.navigation.presenter
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.my.testlearning.data.ShoppingRepository
import io.my.testlearning.ui.presenter.ShoppingPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class MainActivityKtTest: TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var presenter: ShoppingPresenter

    @Test
    fun addItemToList() = before {
        presenter = ShoppingPresenter(FakeRepository())
    }.after {

    }.run {
        composeTestRule.setContent {

            MainScreen(
                state = presenter.state.collectAsState(),
                intents = presenter.intent
            )
        }

        step("check full price"){
            ComposeScreen.onComposeScreen<ComposeShowItemsScreen>(composeTestRule) {
                sumField {
                    assertTextContains("0,00")
                }
            }
        }

        step("call add item screen"){
            ComposeScreen.onComposeScreen<ComposeShowItemsScreen>(composeTestRule) {
                addItem {
                    performClick()
                }
            }
        }


        step("add item"){
            ComposeScreen.onComposeScreen<ComposeAddItemScreen>(composeTestRule) {
                inputTitle {
                    performTextInput("banana")
                }
                inputAmount {
                    performTextInput("5")
                }
                inputPrice {
                    performTextInput("8.0")
                }
                inputImage {
                    performClick()
                }
            }

            ComposeScreen.onComposeScreen<ComposeChoiseImage>(composeTestRule) {
                choiseImage {
                    assertIsDisplayed()
                }
            }

            ComposeScreen.onComposeScreen<ComposeAddItemScreen>(composeTestRule) {
                saveItem {
                    performClick()
                }
            }
        }

        step("check new value price"){
            ComposeScreen.onComposeScreen<ComposeShowItemsScreen>(composeTestRule) {
                sumField {
                    assertTextEquals("40,00")
                }
            }
        }
    }
}