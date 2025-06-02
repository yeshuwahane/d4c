package com.yeshuwahane.d4c.presenatation.shops

import com.yeshuwahane.d4c.data.utils.DataResource

data class ProductUiState(
    val productResourceState: DataResource<List<Product>> = DataResource.initial()
)


data class Product(
    val productName: String,
    val description: String,
    val subTitle: String,
    val price: String,
    val oldPrice: String,
    val rating: Int,
    val reviews: Int,
    val imageRes: String,
    val isBestSeller: Boolean = false,
    val inStock: Boolean = true
)
