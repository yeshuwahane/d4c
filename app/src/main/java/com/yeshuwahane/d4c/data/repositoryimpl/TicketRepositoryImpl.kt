package com.yeshuwahane.d4c.data.repositoryimpl

import com.google.gson.Gson
import com.yeshuwahane.d4c.data.api.AppApi
import com.yeshuwahane.d4c.data.dto.tickets.TicketErrorResponse
import com.yeshuwahane.d4c.data.dto.tickets.TicketsDto
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.data.utils.safeApiCall
import com.yeshuwahane.d4c.domain.repository.TicketRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject


class TicketRepositoryImpl @Inject constructor(
    private val api: AppApi
): TicketRepository {

    override suspend fun submitTicket(
        token: String,
        ticketType: String,
        message: String,
        image: File?
    ): DataResource<TicketsDto> {
        return try {
            val result = api.submitTicket(
                authHeader = token,
                ticketType = ticketType.toRequestBody("text/plain".toMediaTypeOrNull()),
                message = message.toRequestBody("text/plain".toMediaTypeOrNull()),
                files = image?.let {
                    val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("files", it.name, requestFile)
                }
            )
            DataResource.success(result)
        } catch (e: HttpException) {
            val errorMessage = try {
                val errorBody = e.response()?.errorBody()?.string()
                val parsed = Gson().fromJson(errorBody, TicketErrorResponse::class.java)
                parsed.message
            } catch (ex: Exception) {
                "Unexpected error"
            }
            DataResource.error(Throwable(errorMessage))
        } catch (e: Exception) {
            DataResource.error(Throwable(e.localizedMessage ?: "Unexpected error"))
        }
    }

}