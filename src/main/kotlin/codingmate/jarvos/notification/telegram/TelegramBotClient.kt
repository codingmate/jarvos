package codingmate.jarvos.notification.telegram

import codingmate.jarvos.notification.telegram.response.TelegramGetUpdatesResponse
import codingmate.jarvos.notification.telegram.response.TelegramSendMessageResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "telegram", url = "\${api.telegramBot.url}")
interface TelegramBotClient {

    @GetMapping(value = ["/bot{token}/getUpdates"])
    fun getUpdates(
        @PathVariable("token") token: String,
    ): TelegramGetUpdatesResponse
    @GetMapping(value = ["/bot{token}/sendmessage"])
    fun sendMessage(
        @PathVariable("token") token: String,
        @RequestParam("chat_id") chatId: String,
        @RequestParam("text") text: String,
    ): TelegramSendMessageResponse
}


