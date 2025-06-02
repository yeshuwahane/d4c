package com.yeshuwahane.d4c.data.api

import com.yeshuwahane.d4c.data.dto.auth.SendOtpDto
import com.yeshuwahane.d4c.data.dto.auth.SendOtpRequest
import com.yeshuwahane.d4c.data.dto.auth.VerifyOtpDto
import com.yeshuwahane.d4c.data.dto.auth.VerifyOtpRequest
import com.yeshuwahane.d4c.data.dto.product.ProductListDto
import com.yeshuwahane.d4c.data.dto.tickets.TicketsDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AppApi {

    @POST("api/auth/send-otp")
    suspend fun sendOtp(
        @Body request: SendOtpRequest
    ): SendOtpDto

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): VerifyOtpDto

    @GET("api/product")
    suspend fun getProducts(
        @Header("Authorization") authHeader: String
    ): ProductListDto



    @Multipart
    @POST("api/ticket")
    suspend fun submitTicket(
        @Header("Authorization") authHeader: String,
        @Part("ticketType") ticketType: RequestBody,
        @Part("message") message: RequestBody,
        @Part files: MultipartBody.Part? = null
    ): TicketsDto



}

