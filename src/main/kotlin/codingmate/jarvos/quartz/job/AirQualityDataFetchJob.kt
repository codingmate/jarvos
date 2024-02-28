package codingmate.jarvos.quartz.job

import codingmate.jarvos.air_quality.AirQualityService
import codingmate.jarvos.data.entity.ParticularMatterInfo
import codingmate.jarvos.data.repository.history.ParticularMatterInfoRepository
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AirQualityDataFetchJob(
    private val airQualityService: AirQualityService,
    private val pmiRepo: ParticularMatterInfoRepository

): Job{
    override fun execute(context: JobExecutionContext) {
        val stationName = "은평구"
        val latestHourlyAirQuality = airQualityService.fetchLatestHourlyAirQuality(stationName)
        pmiRepo.save(
            ParticularMatterInfo(
                stationName = stationName,
                createAt = LocalDateTime.now(),
                pm25Value24H = latestHourlyAirQuality.pm25Value24.toInt(),
                pm10Value24H = latestHourlyAirQuality.pm10Value24.toInt(),

                pm25Value = latestHourlyAirQuality.pm25Value.toInt(),
                pm10Value = latestHourlyAirQuality.pm10Value.toInt(),

                pm25Grade24H = latestHourlyAirQuality.pm25Grade.toInt(),
                pm10Grade24H = latestHourlyAirQuality.pm10Grade.toInt(),

                pm25Grade1H = latestHourlyAirQuality.pm25Grade1h.toInt(),
                pm10Grade1H = latestHourlyAirQuality.pm10Grade1h.toInt(),
            )
        )
    }
}