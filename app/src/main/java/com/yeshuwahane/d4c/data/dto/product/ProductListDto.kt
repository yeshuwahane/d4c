package com.yeshuwahane.d4c.data.dto.product


import com.google.gson.annotations.SerializedName



data class ProductListDto(
    @SerializedName("data")
    val data: List<ProductData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)


data class Image(
    @SerializedName("key")
    val key: String,
    @SerializedName("primary")
    val primary: Boolean,
    @SerializedName("url")
    val url: String
)


data class ProductData(
    @SerializedName("_id")
    val id: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("inCartQuantity")
    val inCartQuantity: Int,
    @SerializedName("isBestSeller")
    val isBestSeller: Boolean,
    @SerializedName("isInCart")
    val isInCart: Boolean,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("maxBuyQuantity")
    val maxBuyQuantity: Int,
    @SerializedName("mrp")
    val mrp: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("reviewsCount")
    val reviewsCount: Int,
    @SerializedName("reviewsRating")
    val reviewsRating: Int,
    @SerializedName("stockStatus")
    val stockStatus: String,
    @SerializedName("subTitle")
    val subTitle: String,
    @SerializedName("subTitle2")
    val subTitle2: String,
    @SerializedName("title")
    val title: String
)