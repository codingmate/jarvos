package codingmate.jarvos.quartz.job

import codingmate.jarvos.air_quality.AirQualityService
import codingmate.jarvos.data.repository.ParticularMatterInfoRepository
import codingmate.jarvos.data.repository.history.TelegramBotApiUseHistoryRepository
import codingmate.jarvos.telegram.TelegramBotService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

// 실시간 미세먼지 농도를 확인하고 이전 발송에서 미세먼지 상태가 "좋음"으로 발송되었지만, 실시간 미세먼지 농도가 "좋음"이 아니라면, 리마인드 알림 전송
@Component
class ParticularMatterReCheckJob(
        private val telegramBotService: TelegramBotService,
        private val airQualityService: AirQualityService,
        private val tbauhRepository: TelegramBotApiUseHistoryRepository,
        private val pmiRepo: ParticularMatterInfoRepository
): Job {
    override fun execute(p0: JobExecutionContext?) {
        println("ParticularMatterReCheckJob 실행")
        // telegram 전송 내역을 확인하여 오늘 마지막으로 보낸 메시지가 없으면 실행하지 않음
        val todayLastNotification = tbauhRepository.findLatestToday() ?: return
        // 마찬가지로 etc == "좋음"이 아니라면, 실행하지 않음
        if(todayLastNotification.etc != "좋음")
            return

        val latestPmi = pmiRepo.findLatest() ?: return
        if ( latestPmi.pm25Grade1H == 1 ) return // 여전히 미세먼지가 좋을 경우도 실행하지 않음

        val pm25Status = airQualityService.getAirStatusByGrade(latestPmi.pm25Grade1H)
        val pm10Status = airQualityService.getAirStatusByGrade(latestPmi.pm10Grade1H)
        val pm25Status24H = airQualityService.getAirStatusByGrade(latestPmi.pm25Grade24H)
        val pm10Status24H = airQualityService.getAirStatusByGrade(latestPmi.pm10Grade24H)


        val notificationMessage = """
            "☆★☆★ 미세먼지 상태 악화 ☆★☆★"
            "☆★☆★ 마스크 꼭 착용하기 ☆★☆★" 
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
        // 마지막 미세먼지 양을 조회하여, "좋음"이 아니라면 미세먼지 "나쁨" 상태
    }
}