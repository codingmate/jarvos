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
    @Value("\${api.telegramBot.chatId.wade}")
    private val wadeChatId: String,
    @Value("\${api.telegramBot.chatId.ember}")
    private val emberChatId: String,
) {
    fun getUpdates(token: String = testKey, offset: Long? = null, limit: Int? = null, timeout: Int? = null): TelegramGetUpdatesResponse {
        return telegramBotClient.getUpdates(token, offset, limit, timeout)
    }

    fun sendMessageToEmberAndWade(message: String) {
        this.sendMessage(chatId = wadeChatId, message = message)
        this.sendMessage(chatId = emberChatId, message = message)
    }
    fun sendMessage(token: String = testKey, chatId: String = wadeChatId, message: String = "Dream Comes True", parseMode: String? = null,
                    disableWebPagePreview: Boolean? = null, disableNotification: Boolean? = null): TelegramBotSendMessageResponse {
        return telegramBotClient.sendMessage(token, chatId, message, parseMode, disableWebPagePreview, disableNotification)
    }

}