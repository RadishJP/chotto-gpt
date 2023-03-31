package com.radish.chotto

import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Controller
class ChatController(
        @Value("\${slack.channel}") private val slackChannel: String,
        private val slackService: SlackService,
        private val messageRepository: MessageRepository
) {
    @GetMapping("/")
    fun chatPage(model: Model): String {
        model.addAttribute("messages", messageRepository.findAll())
        return "chat"
    }

//    @PostMapping("/send-message")
//    fun sendMessage(@RequestParam("message") message: String): String {
//        val slackMessage = slackService.postMessage(slackChannel, message)
//        val timestamp = Instant.ofEpochMilli((slackMessage.ts.toDouble() * 1000).toLong())
//            .atZone(ZoneId.systemDefault())
//            .toLocalDateTime()
//        val text = slackMessage.message.text
//        messageRepository.save(Message(text = text, timestamp = timestamp))
//        return "redirect:/"
//    }

    @PostMapping("/send-message")
    fun sendMessage(@RequestParam("message") message: String, session: HttpSession): String {
        val userName = session.getAttribute("name") as String
        val text = "[$userName] $message"
        slackService.postMessage(slackChannel, text)
        return "redirect:/"
    }


    @PostMapping("/login")
    fun login(@RequestParam("name") name: String, session: HttpSession): String {
        val threadTs = slackService.createThread(slackChannel, name)
        session.setAttribute("name", name)
        session.setAttribute("threadTs", threadTs)
        return "redirect:/chat"
    }

    @GetMapping("/chat")
    fun getMessages(model: Model, session: HttpSession): String {
        val userName = session.getAttribute("name") as String
        val threadTs = session.getAttribute("threadTs") as String

        val messages = slackService.getThreadReplies(slackChannel, threadTs)
            .map { Message(id = UUID.randomUUID().toString(), text = it.text, timestamp = it.timestamp) }

        model.addAttribute("messages", messages)
        return "chat"
    }

    @GetMapping("/login")
    fun loginPage(): String {
        return "login"
    }

}