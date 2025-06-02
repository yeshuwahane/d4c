package com.yeshuwahane.d4c.data.repositoryimpl

import com.yeshuwahane.d4c.data.api.AppApi
import com.yeshuwahane.d4c.data.mapper.toUiModel
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.data.utils.safeApiCall
import com.yeshuwahane.d4c.domain.repository.ProductRepository
import com.yeshuwahane.d4c.presenatation.features.shops.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val api: AppApi
): ProductRepository {

    override suspend fun getProducts(token: String): DataResource<List<Product>> {
        return safeApiCall(
            apiCall = {api.getProducts(token)},
            mapper = {it.data.map { it.toUiModel() }}
        )
    }
}