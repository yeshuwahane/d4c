package com.yeshuwahane.d4c.data.dto.tickets


import com.google.gson.annotations.SerializedName



data class TicketsDto(
    @SerializedName("data")
    val data: TicketData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)

data class TicketData(
    @SerializedName("closedAt")
    val closedAt: Any,
    @SerializedName("closedBy")
    val closedBy: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("messages")
    val messages: List<Message>,
    @SerializedName("ticketId")
    val ticketId: String,
    @SerializedName("ticketStatus")
    val ticketStatus: String,
    @SerializedName("ticketType")
    val ticketType: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("__v")
    val v: Int
)

data class Message(
    @SerializedName("by")
    val `by`: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: List<Any>,
    @SerializedName("message")
    val message: String,
    @SerializedName("readBy")
    val readBy: List<Any>,
    @SerializedName("userId")
    val userId: String
)