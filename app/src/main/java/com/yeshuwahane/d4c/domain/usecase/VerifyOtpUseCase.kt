package com.yeshuwahane.d4c.domain.usecase

import com.yeshuwahane.d4c.data.dto.auth.VerifyOtpDto
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.data.utils.TokenManager
import com.yeshuwahane.d4c.domain.repository.AuthRepository
import javax.inject.Inject


class VerifyOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) {
    suspend operator fun invoke(
        phoneNumber: String,
        countryCode: String,
        otp: String
    ): DataResource<VerifyOtpDto> {
        val result = authRepository.verifyOtp(phoneNumber, countryCode, otp)

        if (result.isSuccess() && result.data != null) {
            val data = result.data.data
            tokenManager.saveTokens(
                jwt = data.jwt,
                refreshToken = data.refreshToken,
                isExistingUser = data.isExistingUser
            )
        }

        return result
    }
}
