package codingmate.jarvos.notification.telegram

import codingmate.jarvos.notification.telegram.entity.TelegramMessage
import codingmate.jarvos.notification.telegram.repository.TelegramMessageRepository
import codingmate.jarvos.notification.telegram.response.TelegramGetUpdatesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class TelegramBotService(
    private val telegramBotClient: TelegramBotClient,
    private val telegramMessageRepository: TelegramMessageRepository,
    @Value("\${api.telegramBot.Syhan}")
    private val key: String
) {
    private var lastUpdateId: Long = 0

    @Scheduled(fixedDelay = 10000)
    fun pollUpdates() {
        val updatesResponse = telegramBotClient.getUpdates(key)
        updatesResponse.result.forEach { update ->
            processUpdate(update)
            lastUpdateId = update.updateId
        }
    }

    private fun processUpdate(update: TelegramGetUpdatesResponse.Update) {
        update.message?.let { message ->
            // 데이터베이스에 메시지 저장
            val telegramMessage = TelegramMessage(chatId = message.chat.id, message = update.message)
            telegramMessageRepository.save(telegramMessage)

        }
    }
    fun sendMessage(chatId: String, message: String) {
        telegramBotClient.sendMessage(key, chatId, message)
    }
}