package com.yeshuwahane.d4c.domain.repository

import com.yeshuwahane.d4c.data.dto.auth.SendOtpDto
import com.yeshuwahane.d4c.data.dto.auth.VerifyOtpDto
import com.yeshuwahane.d4c.data.utils.DataResource

interface AuthRepository {

    suspend fun sendOtp(phoneNumber: String, countryCode: String): DataResource<SendOtpDto>
    suspend fun verifyOtp(phoneNumber: String, countryCode: String, otp: String): DataResource<VerifyOtpDto>


}