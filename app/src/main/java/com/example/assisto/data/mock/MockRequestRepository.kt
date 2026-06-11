package com.example.assisto.data.mock

import com.example.assisto.data.model.ActiveJob
import com.example.assisto.data.model.ChatMessage
import com.example.assisto.data.model.Conversation
import com.example.assisto.data.model.IncomingJobRequest
import com.example.assisto.data.model.PaymentSummary
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.ServiceSubCategory
import com.example.assisto.data.repository.RequestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
class MockRequestRepository : RequestRepository {

    private val requests = MutableStateFlow(MockData.recentRequests)
    private val messages = MutableStateFlow(MockData.chatMessages.toMutableMap())
    private var activeJob: ActiveJob? = MockData.activeJob
    private var incomingRequests = MutableStateFlow(listOf(MockData.incomingRequest()))

    override fun getSubCategories(category: ServiceCategory): List<ServiceSubCategory> =
        MockData.subCategoryList(category)

    override fun getRecentRequests(): Flow<List<ServiceRequest>> = flow {
        delay(300)
        emit(requests.value.take(3))
    }

    override fun getMyRequests(): Flow<List<ServiceRequest>> = requests

    override fun createRequest(request: ServiceRequest): Flow<ServiceRequest> = flow {
        delay(500)
        requests.update { it + request }
        emit(request)
    }

    override fun getRequestById(id: String): Flow<ServiceRequest?> = flow {
        delay(200)
        emit(requests.value.find { it.id == id })
    }

    override fun acceptRequest(requestId: String, providerId: String): Flow<ServiceRequest> = flow {
        delay(300)
        val updated = requests.value.map { req ->
            if (req.id == requestId) {
                val provider = MockData.providers.find { it.id == providerId }
                req.copy(
                    status = RequestStatus.ACCEPTED,
                    providerId = providerId,
                    providerName = provider?.name,
                )
            } else req
        }
        requests.value = updated
        emit(updated.first { it.id == requestId })
    }

    override fun updateRequestStatus(requestId: String, status: RequestStatus): Flow<ServiceRequest> = flow {
        delay(200)
        val updated = requests.value.map { if (it.id == requestId) it.copy(status = status) else it }
        requests.value = updated
        activeJob = activeJob?.copy(status = status)
        emit(updated.first { it.id == requestId })
    }

    override fun getConversations(): Flow<List<Conversation>> = flow {
        delay(300)
        emit(MockData.conversations)
    }

    override fun getChatMessages(conversationId: String): Flow<List<ChatMessage>> = flow {
        emit(messages.value[conversationId] ?: emptyList())
    }

    override fun sendMessage(conversationId: String, content: String, isFromSeeker: Boolean): Flow<ChatMessage> = flow {
        val msg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderId = if (isFromSeeker) "seeker" else "provider",
            senderName = if (isFromSeeker) "You" else "Provider",
            content = content,
            timestamp = "Now",
            isFromSeeker = isFromSeeker,
        )
        messages.update { map ->
            val updated = map.toMutableMap()
            updated[conversationId] = (updated[conversationId] ?: emptyList()) + msg
            updated
        }
        emit(msg)
    }

    override fun getPaymentSummary(requestId: String): Flow<PaymentSummary> = flow {
        delay(300)
        emit(MockData.paymentSummary(requestId))
    }

    override fun getIncomingRequests(): Flow<List<IncomingJobRequest>> = incomingRequests

    override fun getActiveJob(): Flow<ActiveJob?> = flow { emit(activeJob) }

    override fun submitReview(requestId: String, rating: Int, tags: List<String>, comment: String) {
        // Mock — no-op
    }

    fun simulateIncomingRequest() {
        incomingRequests.value = listOf(MockData.incomingRequest())
    }

    fun clearIncomingRequest(id: String) {
        incomingRequests.value = incomingRequests.value.filter { it.id != id }
    }
}
