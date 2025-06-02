package com.yeshuwahane.d4c.data.dto.auth


import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)