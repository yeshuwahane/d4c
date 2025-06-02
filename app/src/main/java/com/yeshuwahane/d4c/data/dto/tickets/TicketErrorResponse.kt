package com.yeshuwahane.d4c.data.dto.tickets


import com.google.gson.annotations.SerializedName

data class TicketErrorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)