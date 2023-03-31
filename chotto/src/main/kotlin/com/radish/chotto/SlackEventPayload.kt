package com.radish.chotto

import com.fasterxml.jackson.annotation.JsonProperty

data class SlackEventPayload(
        val token: String,
        val teamId: String,
        val apiAppId: String,
        val event: SlackEvent,
        val type: String,
        val eventId: String,
        val eventTime: Long
)

data class SlackEvent(
        val type: String,
        val user: String,
        val text: String,
        val ts: String,
        @JsonProperty("thread_ts") val threadTs: String?
)
