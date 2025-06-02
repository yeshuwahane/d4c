package com.yeshuwahane.d4c.data.mapper

import com.yeshuwahane.d4c.data.dto.product.ProductData
import com.yeshuwahane.d4c.presenatation.shops.Product


fun ProductData.toUiModel(): Product {
    return Product(
        productName = title,
        description = subTitle,
        subTitle = subTitle2,
        price = "₹$price",
        oldPrice = "₹$mrp",
        rating = reviewsRating,
        reviews = reviewsCount,
        imageRes = images.firstOrNull()?.url.orEmpty(),
        isBestSeller = isBestSeller,
        inStock = stockStatus.equals("In Stock", ignoreCase = true)
    )
}
