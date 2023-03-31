package com.radish.chotto

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class MessageHandler : TextWebSocketHandler() {
    private val sessions = mutableListOf<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions += session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions -= session
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        // Handle incoming messages if necessary
    }

    fun broadcastMessage(text: String) {
        val message = TextMessage(text)
        sessions.forEach { session ->
            session.sendMessage(message)
        }
    }
}
