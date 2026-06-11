package com.example.assisto.data.mock

import com.example.assisto.data.model.ChatMessage
import com.example.assisto.data.model.Conversation
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Convenience facade over [MockData] exposing every dataset as a [Flow] with the
 * spec's 800ms simulated network delay. The feature repositories remain the
 * primary data source for the DI graph; this wraps the same data for callers that
 * want the spec's unified mock surface.
 */
object MockRepository {

    private const val DELAY_MS = 800L

    fun providers(): Flow<List<Provider>> = emitDelayed(MockData.providers)

    fun seekers(): Flow<List<UserProfile>> = emitDelayed(MockData.seekers)

    fun requests(): Flow<List<ServiceRequest>> = emitDelayed(MockData.recentRequests)

    fun conversations(): Flow<List<Conversation>> = emitDelayed(MockData.conversations)

    fun messages(threadId: String): Flow<List<ChatMessage>> =
        emitDelayed(MockData.chatMessages[threadId] ?: emptyList())

    private fun <T> emitDelayed(value: T): Flow<T> = flow {
        delay(DELAY_MS)
        emit(value)
    }
}
