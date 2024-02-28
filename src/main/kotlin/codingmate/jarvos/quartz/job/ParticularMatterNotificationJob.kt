package codingmate.jarvos.quartz.job

import codingmate.jarvos.air_quality.AirQualityService
import codingmate.jarvos.data.repository.history.ParticularMatterInfoRepository
import codingmate.jarvos.telegram.TelegramBotService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ParticularMatterNotificationJob(
    private val telegramBotService: TelegramBotService,
    private val airQualityService: AirQualityService,
    private val pmiRepo: ParticularMatterInfoRepository

): Job{
    override fun execute(context: JobExecutionContext) {

        val latestPmi = pmiRepo.findLatest() ?: return

        val notificationMessage = when {
            latestPmi.pm25Grade1H == 1 -> "!오늘은 안전!"
            else -> "☆★☆★ 마스크 꼭 쓰기 ☆★☆★"
        }

        val pm25Status = airQualityService.getAirStatusByGrade(latestPmi.pm25Grade1H)
        val pm10Status = airQualityService.getAirStatusByGrade(latestPmi.pm10Grade1H)
        val pm25Status24H = airQualityService.getAirStatusByGrade(latestPmi.pm25Grade24H)
        val pm10Status24H = airQualityService.getAirStatusByGrade(latestPmi.pm10Grade24H)

        val message = """
            $notificationMessage
            미세먼지(PM 10) : ${latestPmi.pm10Value}(${pm10Status})
            초미세먼지(PM 2.5) : ${latestPmi.pm25Value}(${pm25Status})
            
            *24시간*
            PM 10, PM 2.5 농도(µg/m²)
            측정일시 : ${latestPmi.createAt}
            측정장소 : ${latestPmi.stationName}
            미세먼지(PM 10) : ${latestPmi.pm10Value24H}(${pm10Status24H})
            초미세먼지(PM 2.5) : ${latestPmi.pm25Value24H}(${pm25Status24H})
        """.trimIndent()

        telegramBotService.sendMessageToEmberAndWade(etc = pm25Status, message = message)
    }
}