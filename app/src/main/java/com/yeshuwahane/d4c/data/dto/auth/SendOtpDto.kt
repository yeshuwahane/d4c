package com.yeshuwahane.d4c.data.dto.auth


import com.google.gson.annotations.SerializedName

data class SendOtpDto(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)

class Data