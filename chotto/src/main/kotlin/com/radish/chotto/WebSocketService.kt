package com.radish.chotto

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class WebSocketService(private val messagingTemplate: SimpMessagingTemplate) {

    fun broadcastMessage(message: Any) {
        messagingTemplate.convertAndSend("/topic/chat", message)
    }
}