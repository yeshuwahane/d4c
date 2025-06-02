package com.yeshuwahane.d4c.domain.repository

import com.yeshuwahane.d4c.data.dto.product.ProductListDto
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.presenatation.shops.Product

interface ProductRepository {

    suspend fun getProducts(token: String): DataResource<List<Product>>
}