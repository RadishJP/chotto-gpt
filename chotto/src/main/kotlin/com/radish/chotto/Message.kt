package com.radish.chotto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Message(
        @Id
        val id: String? = null,
        val text: String,
        val timestamp: LocalDateTime
)