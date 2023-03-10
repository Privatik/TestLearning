package io.my.testlearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.presenter
import io.my.testlearning.ui.presenter.ShoppingIntent
import io.my.testlearning.ui.presenter.ShoppingPresenter
import io.my.testlearning.ui.presenter.ShoppingState
import io.my.testlearning.ui.screens.AddShoppingItem
import io.my.testlearning.ui.screens.ShoppingItems
import io.my.testlearning.ui.theme.TestLearningTheme
import io.my.testlearning.util.BasePresenterAdapter
import io.my.testlearning.util.TestTags

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val owner = AndroidPresenterStoreOwner(BasePresenterAdapter())

        setContent {
            TestLearningTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                    ,
                    color = MaterialTheme.colors.primary
                ) {
                    PresenterCompositionLocalProvider(
                        owner = owner
                    ) {
                        val presenter: ShoppingPresenter = presenter(factory = presenterFactory())

                        MainScreen(
                            state = presenter.state.collectAsState(),
                            intents = presenter.intent
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    state: State<ShoppingState>,
    intents: ShoppingIntent,
){
    val shoppingItems = remember { derivedStateOf { state.value.items } }
    val images = remember { derivedStateOf { state.value.images } }
    val totalPrice = remember { derivedStateOf { state.value.totalPrice } }
    val isOpenBottomBar by remember { derivedStateOf { state.value.isOpenBottomBar } }

    BackHandler(enabled = isOpenBottomBar) { intents.openBottomBar(false) }

    var heightBottom by remember { mutableStateOf(0) }
    val height by animateIntAsState(
        targetValue = if (isOpenBottomBar) heightBottom else 0
    )

    Box(
        modifier = Modifier.onSizeChanged {
            if (heightBottom != it.height){
                heightBottom = it.height
            }
        }
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(0, -height) }
                .fillMaxSize()
                .semantics { testTag = TestTags.showItemsScreen }
        ) {
            ShoppingItems(
                modifier = Modifier.fillMaxSize(),
                shoppingItems = shoppingItems,
                totalPrice = totalPrice
            )
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .semantics { testTag = TestTags.addItemTag },
                onClick = { intents.openBottomBar(true) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    tint = Color.White,
                    contentDescription = "Add"
                )
            }
        }
        AddShoppingItem(
            modifier = Modifier
                .offset { IntOffset(0, heightBottom - height) }
                .fillMaxSize()
                .semantics { testTag = TestTags.addItemScreen },
            images = images,
            searchImages = { intents.searchImages(it) },
            addItem = {
                intents.addItem(it)
                intents.openBottomBar(false)
            }
        )
    }
}

