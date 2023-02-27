package io.my.testlearning.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val hits: List<ImageResult>,
)

@Serializable
data class ImageResult(
    val userImageURL: String,
)

//data class ImageResult(
//    val comments: Int,
//    val downloads: Int,
//    val favorites: Int,
//    val id: Int,
//    val imageHeight: Int,
//    val imageSize: Int,
//    val imageWidth: Int,
//    val largeImageURL: String,
//    val likes: Int,
//    val pageURL: String,
//    val previewHeight: Int,
//    val previewURL: String,
//    val previewWidth: Int,
//    val tags: String,
//    val type: String,
//    val user: String,
//    @SerialName("user_id")
//    val userId: Int,
//    val userImageURL: String,
//    val views: Int,
//    val webformatHeight: Int,
//    val webformatURL: String,
//    val webformatWidth: Int
//)