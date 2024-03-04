package codingmate.jarvos.notification.telegram

import codingmate.jarvos.notification.telegram.response.TelegramBotSendMessageResponse
import codingmate.jarvos.notification.telegram.response.TelegramBotGetUpdatesResponse
import codingmate.jarvos.quartz.job.AirQualityDataFetchJob
import codingmate.jarvos.quartz.job.ParticularMatterNotificationJob
import codingmate.jarvos.quartz.job.ParticularMatterReCheckJob
import codingmate.jarvos.telegram.TelegramBotService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TelegramBotTestController(
    private val telegramBotService: TelegramBotService,
) {

    // Telegram Bot의 업데이트를 가져오는 엔드포인트
    @GetMapping("/telegram/getUpdates")
    fun getUpdates(): TelegramBotGetUpdatesResponse {
        return telegramBotService.getUpdates()
    }

    // Telegram Bot을 통해 메시지를 보내는 엔드포인트
    @GetMapping("/telegram/sendMessage")
    fun sendMessageTest(): TelegramBotSendMessageResponse {
        return telegramBotService.sendMessage()
    }
}