package com.radish.chotto

import com.slack.api.Slack
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.request.conversations.ConversationsRepliesRequest
import com.slack.api.methods.response.chat.ChatPostMessageResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class SlackService(@Value("\${slack.token}") private val slackApiToken: String) {

    private val slack = Slack.getInstance()

    fun postMessage(channel: String, message: String): ChatPostMessageResponse {
        val apiClient = slack.methods(slackApiToken)
        val result = apiClient.chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channel)
                .text(message)
                .build())
        return result
    }

    fun createThread(channel: String, userName: String): String {
        val response = postMessage(channel, "Thread for @$userName")
        return response.message.ts
    }

    fun getThreadReplies(channel: String, threadTs: String): List<Message> {
        val apiClient = slack.methods(slackApiToken)
        val request = ConversationsRepliesRequest.builder()
            .channel(channel)
            .ts(threadTs)
            .build()
        val response = apiClient.conversationsReplies(request)
        return response.messages?.map {
            val timestamp = Instant.ofEpochMilli((it.ts.toDouble() * 1000).toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            Message(id = UUID.randomUUID().toString(), text = it.text, timestamp = timestamp)
        } ?: emptyList()
    }

}
