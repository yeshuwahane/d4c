package com.yeshuwahane.d4c.data.repositoryimpl

import com.yeshuwahane.d4c.data.api.AppApi
import com.yeshuwahane.d4c.data.dto.auth.SendOtpDto
import com.yeshuwahane.d4c.data.dto.auth.SendOtpRequest
import com.yeshuwahane.d4c.data.dto.auth.VerifyOtpDto
import com.yeshuwahane.d4c.data.dto.auth.VerifyOtpRequest
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.data.utils.TokenManager
import com.yeshuwahane.d4c.data.utils.safeApiCall
import com.yeshuwahane.d4c.domain.repository.AuthRepository
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import javax.inject.Inject



class AuthRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun sendOtp(
        phoneNumber: String,
        countryCode: String
    ): DataResource<SendOtpDto> {
        return safeApiCall(
            apiCall = {
                api.sendOtp(
                    SendOtpRequest(
                        countryCode = countryCode,
                        phoneNumber = phoneNumber
                    )
                )
            },
            mapper = { it }
        )
    }

    override suspend fun verifyOtp(
        phoneNumber: String,
        countryCode: String,
        otp: String
    ): DataResource<VerifyOtpDto> {
        return try {
            safeApiCall(
                apiCall = {
                    api.verifyOtp(
                        VerifyOtpRequest(
                            countryCode = countryCode,
                            otp = otp,
                            phoneNumber = phoneNumber
                        )
                    )
                },
                mapper = { it }
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = e.response()?.message()
            DataResource.error(Throwable(message))
        } catch (e: Exception) {
            DataResource.error(Throwable(e.localizedMessage ?: "Unexpected error"))
        }
    }


}
