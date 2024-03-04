package codingmate.jarvos.telegram

import codingmate.jarvos.data.entity.history.TelegramBotApiUseHistory
import codingmate.jarvos.data.repository.history.TelegramBotApiUseHistoryRepository
import codingmate.jarvos.notification.telegram.TelegramBotClient
import codingmate.jarvos.notification.telegram.response.TelegramBotGetUpdatesResponse
import codingmate.jarvos.notification.telegram.response.TelegramBotSendMessageResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TelegramBotService(
        private val tbahRepo: TelegramBotApiUseHistoryRepository,
        private val telegramBotClient: TelegramBotClient,
        @Value("\${api.telegramBot.syHan.key}")
    private val syHanKey: String,
        @Value("\${api.telegramBot.chatId.wade}")
    private val wadeChatId: String,
        @Value("\${api.telegramBot.chatId.ember}")
    private val emberChatId: String,
) {
    fun getUpdates(token: String = syHanKey, offset: Long? = null, limit: Int? = null, timeout: Int? = null): TelegramBotGetUpdatesResponse {
        val telegramBotGetUpdatesResponse = telegramBotClient.getUpdates(token, offset, limit, timeout)
        // 2024-02-28 21:31) getUpdates는 추후 개발 예정
        tbahRepo.save(
            TelegramBotApiUseHistory(
                createAt = LocalDateTime.now(),
                apiType = "getUpdates",
            )
        )

        return telegramBotGetUpdatesResponse
    }

    fun sendMessageToEmberAndWade(etc: String, message: String) {
        this.sendMessage(chatId = wadeChatId, etc = etc, message = message)
        this.sendMessage(chatId = emberChatId, etc = etc, message = message)
    }
    fun sendMessage(token: String = syHanKey, chatId: String = wadeChatId, etc: String = "", message: String = "Dream Comes True", parseMode: String? = null,
                    disableWebPagePreview: Boolean? = null, disableNotification: Boolean? = null): TelegramBotSendMessageResponse {
        val telegramBotSendMessageResponse = telegramBotClient.sendMessage(token, chatId, message, parseMode, disableWebPagePreview, disableNotification)

        tbahRepo.save(
            TelegramBotApiUseHistory(
                createAt = LocalDateTime.now(),
                apiType = "sendMessage",
                etc = etc,
                chatId = chatId,
                message = message
            )
        )

        return telegramBotSendMessageResponse
    }

}