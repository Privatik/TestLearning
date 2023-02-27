package io.my.testlearning.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.my.testlearning.data.dto.ShoppingItemDto
import io.my.testlearning.ui.theme.Purple700
import io.my.testlearning.ui.theme.TestLearningTheme
import io.my.testlearning.util.PictureItem

@Composable
fun ShoppingItems(
    modifier: Modifier = Modifier,
    shoppingItems: State<List<ShoppingItemDto>>,
    totalPrice: State<Double>,
){
    val price = remember(totalPrice.value) { String.format("%.2f", totalPrice.value) }

    val list = remember {
        val item = ShoppingItemDto(
            id = 1L,
            name = "Bananas",
            amount = 6,
            price = 0.25,
            imageUrl = null
        )


        buildList {
            repeat(100) { add(item) }
        }
    }

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            itemsIndexed(list){ index, item ->
                ShoppingItem(
                    modifier = Modifier.fillMaxWidth(),
                    item = item,
                    openSettingItem = {}
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple700, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .padding(20.dp)
        ) {
            Text(text = price)
        }
    }
}

@Preview
@Composable
fun PreviesShoppingItem(){
    val item = remember {
        ShoppingItemDto(
            id = 1L,
            name = "Bananas",
            amount = 6,
            price = 0.25,
            imageUrl = null
        )
    }
    TestLearningTheme() {
        ShoppingItem(
            modifier = Modifier.fillMaxWidth(),
            item = item,
            openSettingItem = {}
        )
    }
}

@Composable
private fun ShoppingItem(
    modifier: Modifier = Modifier,
    item: ShoppingItemDto,
    openSettingItem: () -> Unit
){
    val picture = remember(item.imageUrl) { PictureItem.Remote(item.imageUrl.orEmpty()) }
    val sumText = remember(item.amount, item.price) {
        "${item.amount} x ${item.price} = ${item.amount * item.price}"
    }

    Card(
        modifier = modifier
            .clickable { openSettingItem() },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(vertical = 2.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.foundation.Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(5.dp)),
                painter = picture.painter(),
                contentDescription = "Image"
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(text = item.name)
                Text(text = sumText)
            }
        }
    }
}