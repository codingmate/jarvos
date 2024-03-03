package codingmate.jarvos.quartz.job

import codingmate.jarvos.air_quality.AirQualityService
import codingmate.jarvos.data.repository.ParticularMatterInfoRepository
import codingmate.jarvos.data.repository.history.TelegramBotApiUseHistoryRepository
import codingmate.jarvos.telegram.TelegramBotService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component


// 실시간 미세먼지 농도를 확인하여 알림 전송
@Component
class ParticularMatterNotificationJob(
        private val telegramBotService: TelegramBotService,
        private val airQualityService: AirQualityService,
        private val tbauhRepository: TelegramBotApiUseHistoryRepository,
        private val pmiRepo: ParticularMatterInfoRepository,

        ): Job{
    override fun execute(context: JobExecutionContext) {

        // telegram 전송 내역을 확인하여 오늘 마지막으로 보낸 메시지가 있으면 실행하지 않음
        val todayLastNotification = tbauhRepository.findLatestToday()
        // 오늘 이미 알림이 간 경우, 실행하지 않음
        if( todayLastNotification != null )
            return

        val latestPmi = pmiRepo.findLatest() ?: return


        val pm25Status = airQualityService.getAirStatusByGrade(latestPmi.pm25Grade1H)
        val pm10Status = airQualityService.getAirStatusByGrade(latestPmi.pm10Grade1H)
        val pm25Status24H = airQualityService.getAirStatusByGrade(latestPmi.pm25Grade24H)
        val pm10Status24H = airQualityService.getAirStatusByGrade(latestPmi.pm10Grade24H)

        val notificationMessage = """
            ${when {
                latestPmi.pm25Grade1H == 1 -> "!오늘은 안전!"
                else -> "☆★☆★ 마스크 꼭 착용하기 ☆★☆★" }
            }
            미세먼지(PM 10) : ${latestPmi.pm10Value}(${pm10Status})
            초미세먼지(PM 2.5) : ${latestPmi.pm25Value}(${pm25Status})
            
            *24시간*
            PM 10, PM 2.5 농도(µg/m²)
            측정일시 : ${latestPmi.createAt}
            측정장소 : ${latestPmi.stationName}
            미세먼지(PM 10) : ${latestPmi.pm10Value24H}(${pm10Status24H})
            초미세먼지(PM 2.5) : ${latestPmi.pm25Value24H}(${pm25Status24H})
        """.trimIndent()

        telegramBotService.sendMessageToEmberAndWade(etc = pm25Status, message = notificationMessage)
    }
}