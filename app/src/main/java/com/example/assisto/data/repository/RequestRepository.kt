package com.example.assisto.data.repository

import com.example.assisto.data.model.ActiveJob
import com.example.assisto.data.model.ChatMessage
import com.example.assisto.data.model.Conversation
import com.example.assisto.data.model.IncomingJobRequest
import com.example.assisto.data.model.PaymentSummary
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.ServiceSubCategory
import com.example.assisto.data.model.ServiceCategory
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    fun getSubCategories(category: ServiceCategory): List<ServiceSubCategory>
    fun getRecentRequests(): Flow<List<ServiceRequest>>
    fun getMyRequests(): Flow<List<ServiceRequest>>
    fun createRequest(request: ServiceRequest): Flow<ServiceRequest>
    fun getRequestById(id: String): Flow<ServiceRequest?>
    fun acceptRequest(requestId: String, providerId: String): Flow<ServiceRequest>
    fun updateRequestStatus(requestId: String, status: com.example.assisto.data.model.RequestStatus): Flow<ServiceRequest>
    fun getConversations(): Flow<List<Conversation>>
    fun getChatMessages(conversationId: String): Flow<List<ChatMessage>>
    fun sendMessage(conversationId: String, content: String, isFromSeeker: Boolean): Flow<ChatMessage>
    fun getPaymentSummary(requestId: String): Flow<PaymentSummary>
    fun getIncomingRequests(): Flow<List<IncomingJobRequest>>
    fun getActiveJob(): Flow<ActiveJob?>
    fun submitReview(requestId: String, rating: Int, tags: List<String>, comment: String)
}
