package codingmate.jarvos.quartz

import codingmate.jarvos.notification.air_quality.AirQualityService
import codingmate.jarvos.notification.telegram.TelegramBotService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class ParticularMatterNotificationJob(
    private val telegramBotService: TelegramBotService,
    private val airQualityService: AirQualityService
): Job{
    override fun execute(context: JobExecutionContext) {
        val pmStatus = airQualityService.getLatestPmStatus("은평구")
        val message = """
            초미세먼지(PM 2.5), 미세먼지(PM 10) 농도(µg/m²)
            초미세먼지 : ${pmStatus.pm25Value}
            초미세먼지 상태 : ${pmStatus.pm25Status}
            미세먼지 : ${pmStatus.pm10Value}
            미세먼지 상태 : ${pmStatus.pm10Status}
        """.trimIndent()
        //telegramBotService.sendMessage()

        telegramBotService.sendMessage(message = message)
    }
}