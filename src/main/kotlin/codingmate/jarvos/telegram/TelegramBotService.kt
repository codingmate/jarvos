package codingmate.jarvos.notification.telegram

import codingmate.jarvos.notification.telegram.response.TelegramGetUpdatesResponse
import codingmate.jarvos.notification.telegram.response.TelegramBotSendMessageResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TelegramBotService(
    private val telegramBotClient: TelegramBotClient,
    @Value("\${api.telegramBot.test.key}")
    private val testKey: String,
    @Value("\${api.telegramBot.test.chatId}")
    private val testChatId: String,
) {
    fun getUpdates(token: String = testKey, offset: Long? = null, limit: Int? = null, timeout: Int? = null): TelegramGetUpdatesResponse {
        return telegramBotClient.getUpdates(token, offset, limit, timeout)
    }
    fun sendMessage(token: String = testKey, chatId: String = testChatId, message: String = "Dream Comes True", parseMode: String? = null,
                    disableWebPagePreview: Boolean? = null, disableNotification: Boolean? = null): TelegramBotSendMessageResponse {
        return telegramBotClient.sendMessage(token, chatId, message, parseMode, disableWebPagePreview, disableNotification)
    }

}