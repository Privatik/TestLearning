package io.my.testlearning.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.my.testlearning.R

sealed interface PictureItem {

    data class Remote(val url: String) : PictureItem {

        @Composable
        override fun painter() = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .crossfade(true)
                .build(),
        )
    }

    data class Resource(@DrawableRes val id: Int) : PictureItem {

        @Composable
        override fun painter() = painterResource(id = id)
    }

    @Composable
    fun painter(): Painter
}