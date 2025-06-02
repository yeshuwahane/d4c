package com.yeshuwahane.d4c.domain.repository

import com.yeshuwahane.d4c.data.dto.tickets.TicketsDto
import com.yeshuwahane.d4c.data.utils.DataResource
import java.io.File

interface TicketRepository {

    suspend fun submitTicket(
        token: String,
        ticketType: String,
        message: String,
        image: File?
    ): DataResource<TicketsDto>
}