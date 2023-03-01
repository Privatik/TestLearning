package io.my.testlearning.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.my.testlearning.R
import io.my.testlearning.ui.presenter.ShoppingIntent
import io.my.testlearning.ui.theme.Purple200
import io.my.testlearning.util.PictureItem
import io.my.testlearning.util.TestTags
import io.my.testlearning.util.WindowCenterOffsetPositionProvider

@Composable
fun AddShoppingItem(
    modifier: Modifier = Modifier,
    images: State<List<String>>,
    searchImages: (String) -> Unit,
    addItem: (ShoppingIntent.ShoppingItemInsert) -> Unit
) {
    val title = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf(0L) }
    val price = remember { mutableStateOf(0.0) }
    val imageUrl = remember { mutableStateOf<String?>(null) }
    val selectedImage by remember { derivedStateOf { PictureItem.Remote(imageUrl.value.orEmpty()) } }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            ImageWithPopUp(
                painter = selectedImage.painter(),
                images = images,
                findImages = { searchImages(title.value) },
                selectImageUrl = {
                    imageUrl.value = it
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Field(
                modifier = Modifier
                    .semantics { testTag = TestTags.inputTitle },
                text = title,
                hint = stringResource(R.string.name_product),
                onValueChangeListener = { title.value = it }
            )
        }
        Field(
            modifier = Modifier
                .semantics { testTag = TestTags.inputAmount },
            text = amount,
            hint = stringResource(R.string.name_product),
            checkOnEmpty = { value -> value == 0L },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChangeListener = { amount.value = if (it.isNotBlank()) it.toLong() else 0 }
        )
        Field(
            modifier = Modifier
                .semantics { testTag = TestTags.inputPrice },
            text = price,
            hint = stringResource(R.string.name_product),
            checkOnEmpty = { value -> value == 0.0 },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChangeListener = { price.value = if (it.isNotBlank()) it.toDouble() else 0.0 }
        )
        Button(
            modifier = Modifier
                .semantics { testTag = TestTags.saveItem },
            onClick = {
            val _title = title.value
            val _amount = amount.value
            val _price = price.value

            if (_title.isNotBlank() && _amount != 0L && _price != 0.0){
                addItem(
                    ShoppingIntent.ShoppingItemInsert(
                        name = _title,
                        amount = _amount,
                        price = _price,
                        imageUrl = selectedImage.url
                    )
                )

            }
        }) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
fun ImageWithPopUp(
    images: State<List<String>>,
    painter: Painter,
    findImages: () -> Unit,
    selectImageUrl: (String) -> Unit,
){
    var popControl by remember { mutableStateOf(false) }

    Box {
        androidx.compose.foundation.Image(
            modifier = Modifier
                .clickable {
                    findImages()
                    popControl = true
                }
                .semantics { testTag = TestTags.inputImage },
            painter = painter,
            contentDescription = "Find_Image"
        )
        if (popControl){
            Popup(
                popupPositionProvider = WindowCenterOffsetPositionProvider(),
                onDismissRequest = { popControl = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    excludeFromSystemGesture = true,
                    clippingEnabled = false
                )
            ) {
                Column(
                    modifier = Modifier
                        .background(Purple200.copy(alpha = 0.7f))
                        .semantics { testTag = TestTags.choiseImagePop }
                ) {
                    Images(images = images, selectImageUrl = selectImageUrl)
                }
            }
        }
    }
}

@Composable
private fun <T> Field(
    modifier: Modifier = Modifier,
    text: State<T>,
    hint: String,
    checkOnEmpty: (T) -> Boolean = { _ -> false },
    keyboardOptions: KeyboardOptions =  KeyboardOptions.Default,
    onValueChangeListener: (String) -> Unit
) {
    val field by remember(text.value) {
        mutableStateOf(if (checkOnEmpty(text.value)) "" else text.value.toString())
    }

    TextField(
        modifier = modifier,
        value = field,
        placeholder = {
            Text(text = hint)
        },
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChangeListener
    )
}

@Composable
private fun Images(
    modifier: Modifier = Modifier,
    images: State<List<String>>,
    selectImageUrl: (String) -> Unit
){
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(items = images.value) { url ->
            Image(imageUrl = url, selectImageUrl = selectImageUrl)
        }
    }
}

@Composable
private fun Image(
    imageUrl: String,
    selectImageUrl: (String) -> Unit
){
    val painter = remember(imageUrl) { PictureItem.Remote(imageUrl) }
    androidx.compose.foundation.Image(
        modifier = Modifier
            .clickable { selectImageUrl(painter.url) }
            .semantics { testTag = TestTags.choiseImage },
        painter = painter.painter(),
        contentDescription = imageUrl
    )
}