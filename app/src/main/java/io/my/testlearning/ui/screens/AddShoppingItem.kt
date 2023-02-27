package io.my.testlearning.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import io.my.testlearning.R
import io.my.testlearning.ui.theme.Purple200
import io.my.testlearning.util.PictureItem
import io.my.testlearning.util.WindowCenterOffsetPositionProvider

@Composable
fun AddShoppingItem(
    modifier: Modifier = Modifier,
    images: State<List<String>>,
    searchImages: (String) -> Unit
) {
    val title = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf(0) }
    val price = remember { mutableStateOf(0) }
    val selectedImage by remember { derivedStateOf { PictureItem.Remote(title.value) }  }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            ImageWithPopUp(
                painter = selectedImage.painter(),
                images = images,
                findImages = { searchImages(title.value) }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Field(
                text = title,
                hint = stringResource(R.string.name_product),
                onValueChangeListener = { title.value = it }
            )
        }
        Field(
            text = amount,
            hint = stringResource(R.string.name_product),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChangeListener = { amount.value = it.toInt() }
        )
        Field(
            text = price,
            hint = stringResource(R.string.name_product),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChangeListener = { price.value = it.toInt() }
        )
    }
}

@Composable
fun ImageWithPopUp(
    images: State<List<String>>,
    painter: Painter,
    findImages: () -> Unit
){
    var popControl by remember { mutableStateOf(false) }

    Box {
        androidx.compose.foundation.Image(
            modifier = Modifier
                .clickable {
                    findImages()
                    popControl = true
                },
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
                    modifier = Modifier.background(Purple200.copy(alpha = 0.7f))
                ) {
                    Images(images = images)
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
    keyboardOptions: KeyboardOptions =  KeyboardOptions.Default,
    onValueChangeListener: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = text.value.toString(),
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
){
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(items = images.value) { url ->
            Image(imageUrl = url)
        }
    }
}

@Composable
private fun Image(
    imageUrl: String,
){
    val painter = remember(imageUrl) { PictureItem.Remote(imageUrl) }
    androidx.compose.foundation.Image(
        painter = painter.painter(),
        contentDescription = imageUrl
    )
}