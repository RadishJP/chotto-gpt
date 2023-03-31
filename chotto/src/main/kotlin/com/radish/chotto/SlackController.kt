package com.radish.chotto

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SlackController @Autowired constructor(
        private val messageHandler: MessageHandler
) {
    @PostMapping("/slack/events")
    fun handleSlackEvent(@RequestBody eventPayload: SlackEventPayload) {
        val event = eventPayload.event

        if (event.threadTs != null) {
            val userName = event.user
            val text = event.text

            // メッセージをWebSocketで送信
            messageHandler.broadcastMessage("$userName: $text")
        }
    }
}
