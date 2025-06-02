package com.yeshuwahane.d4c.presenatation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.d4c.data.utils.TokenManager
import com.yeshuwahane.d4c.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var phoneNumber by mutableStateOf("9899500873")
    var otp by mutableStateOf("")
    var showOtpField by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isOtpLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var navigateToProductScreen by mutableStateOf(false)

    fun sendOtp() {
        viewModelScope.launch {
            isLoading = true
            val result = authRepository.sendOtp(phoneNumber, "91")
            isLoading = false
            result.takeIf { it.isSuccess() }?.let {
                showOtpField = true
            } ?: run {
                errorMessage = result.error?.toUserFriendlyMessage()

            }
        }
    }

    fun verifyOtp() {
        viewModelScope.launch {
            isOtpLoading = true
            val result = authRepository.verifyOtp(phoneNumber, "91", otp)
            isOtpLoading = false

            if (result.isSuccess()) {
                val data = result.data?.data
                data?.let {
                    viewModelScope.launch {
                        tokenManager.saveTokens(
                            jwt = it.jwt,
                            refreshToken = it.refreshToken,
                            isExistingUser = it.isExistingUser
                        )
                        navigateToProductScreen = true
                    }
                } ?: run {
                    errorMessage = result.error?.toUserFriendlyMessage()

                }
            } else {
                errorMessage = result.error?.toUserFriendlyMessage()

            }
        }
    }

    fun clearError() {
        errorMessage = null
    }

    fun Throwable.toUserFriendlyMessage(): String {
        return when {
            this.message?.contains("400") == true -> "Invalid OTP."
            this.message?.contains("401") == true -> "Unauthorized. Please try again."
            this.message?.contains(
                "timeout",
                ignoreCase = true
            ) == true -> "Connection timeout. Try again."

            else -> "Something went wrong. Please try again."
        }
    }

}
