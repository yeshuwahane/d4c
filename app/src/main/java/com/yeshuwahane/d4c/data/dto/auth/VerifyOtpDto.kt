package com.yeshuwahane.d4c.data.dto.auth


import com.google.gson.annotations.SerializedName

data class VerifyOtpDto(
    @SerializedName("data")
    val data: VerifyOtpData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)


data class VerifyOtpData(
    @SerializedName("isExistingUser")
    val isExistingUser: Boolean,
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)