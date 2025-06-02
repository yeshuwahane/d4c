package com.yeshuwahane.d4c.presenatation.features.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.data.utils.TokenManager
import com.yeshuwahane.d4c.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val tokenManager: TokenManager
) : ViewModel() {


    private val _productUiState = MutableStateFlow(ProductUiState())
    val productUiState = _productUiState.asStateFlow()

    fun getProducts() {
        _productUiState.update {
            it.copy(productResourceState = DataResource.loading())
        }

        viewModelScope.launch {
            try {
                val jwt = tokenManager.jwtTokenFlow.firstOrNull()
                if (jwt.isNullOrEmpty()) {
                    _productUiState.update {
                        it.copy(productResourceState = DataResource.error(Throwable("JWT token is missing")))
                    }
                    return@launch
                }

                val response = repository.getProducts("Bearer $jwt")
                _productUiState.update {
                    it.copy(productResourceState = response)
                }
            } catch (e: Exception) {
                _productUiState.update {
                    it.copy(
                        productResourceState = DataResource.error(
                            Throwable(
                                e.message ?: "Unexpected error"
                            )
                        )
                    )
                }
            }
        }
    }


}