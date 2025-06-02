package com.yeshuwahane.d4c.data.dto.auth


import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)