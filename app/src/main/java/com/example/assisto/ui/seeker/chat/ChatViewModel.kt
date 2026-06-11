package com.example.assisto.ui.seeker.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.ChatMessage
import com.example.assisto.data.repository.RequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel constructor(
    private val requestRepository: RequestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val conversationId: String = savedStateHandle.get<String>("conversationId") ?: "conv1"
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input.asStateFlow()

    init {
        viewModelScope.launch {
            requestRepository.getChatMessages(conversationId).collect { _messages.value = it }
        }
    }

    fun updateInput(v: String) { _input.value = v }

    fun send() {
        val text = _input.value.trim()
        if (text.isEmpty()) return
        viewModelScope.launch {
            requestRepository.sendMessage(conversationId, text, isFromSeeker = true).collect {
                _input.value = ""
                requestRepository.getChatMessages(conversationId).collect { msgs -> _messages.value = msgs }
            }
        }
    }
}
