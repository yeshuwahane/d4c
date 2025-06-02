package com.yeshuwahane.d4c.presenatation.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.d4c.data.dto.tickets.TicketsDto
import com.yeshuwahane.d4c.data.utils.DataResource
import com.yeshuwahane.d4c.data.utils.TokenManager
import com.yeshuwahane.d4c.domain.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _ticketState = MutableStateFlow<DataResource<TicketsDto>>(DataResource.initial())
    val ticketState = _ticketState.asStateFlow()

    fun submitTicket(ticketType: String, message: String, image: File?) {
        viewModelScope.launch {
            _ticketState.value = DataResource.loading()

            val jwt = tokenManager.jwtTokenFlow.firstOrNull()
            if (jwt.isNullOrEmpty()) {
                _ticketState.value = DataResource.error(Throwable("JWT token is missing"))
                return@launch
            }

            val response = ticketRepository.submitTicket(
                token = "Bearer $jwt",
                ticketType = ticketType,
                message = message,
                image = image
            )
            _ticketState.update {
                it.copy(
                    resourceState = response.resourceState,
                    error = response.error,
                    data = response.data
                )
            }
        }
    }
}
