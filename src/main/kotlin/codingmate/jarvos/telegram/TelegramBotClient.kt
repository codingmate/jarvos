package codingmate.jarvos.notification.telegram

import codingmate.jarvos.notification.telegram.response.TelegramBotGetUpdatesResponse
import codingmate.jarvos.notification.telegram.response.TelegramBotSendMessageResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
@FeignClient(name = "telegramBot", url = "\${api.telegramBot.url}")
interface TelegramBotClient {

    @GetMapping(value = ["/bot{token}/getUpdates"])
    fun getUpdates(
        @PathVariable("token") token: String, // Telegram Bot 토큰
        @RequestParam("offset") offset: Long? = null, // 업데이트 처리를 시작할 메시지 ID
        @RequestParam("limit") limit: Int? = null, // 한 번의 요청에 반환할 업데이트 개수의 최대값
        @RequestParam("timeout") timeout: Int? = null // Long Polling 타임아웃 시간(초 단위)
    ): TelegramBotGetUpdatesResponse
    @GetMapping(value = ["/bot{token}/sendmessage"])
    fun sendMessage(
        @PathVariable("token") token: String, // Telegram Bot 토큰
        @RequestParam("chat_id") chatId: String, // 메시지를 보낼 채팅 ID
        @RequestParam("text") text: String, // 전송할 메시지의 텍스트
        @RequestParam("parse_mode") parseMode: String? = null, // 메시지의 서식 지정 방식(Markdown, HTML 등)
        @RequestParam("disable_web_page_preview") disableWebPagePreview: Boolean? = null, // URL 미리보기 비활성화 여부
        @RequestParam("disable_notification") disableNotification: Boolean? = null // 알림 비활성화 여부
    ): TelegramBotSendMessageResponse
}


