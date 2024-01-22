package codingmate.jarvos.notification.air_quality

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AirQualityService(
    private val airQualityClient: AirQualityClient,
    @Value("\${api.airQuality.enCodingKey}")
    val enKey: String
) {

    private fun gradeToStatus(grade: String): String {
        return when(grade) {
            "1" -> "좋음"
            "2" -> "보통"
            "3" -> "나쁨"
            "4" -> "매우나쁨"
            else -> throw IllegalArgumentException("예기치 못한 Value: $grade")
        }
    }
    data class PmStatus(
        val stationName: String,
        val dataTime: String?,
        val pm25Value: String?,
        val pm25Status: String?,
        val pm10Value: String?,
        val pm10Status: String?,
    )
    fun getLatestPmStatus(stationName: String): PmStatus {

        val latestData = airQualityClient.getLatestData(stationName = stationName, serviceKey = enKey)
        val items = latestData.response.body.items
        if (items.isEmpty())
            throw RuntimeException("No Data: items")

        val latestItem = items[0]

        return PmStatus(
            stationName, // 측정소 이름
            latestItem.dataTime, // 측정일시
            latestItem.pm25Value, // 미세먼지 pm2.5 농도
            gradeToStatus(latestItem.pm25Grade1h),
            latestItem.pm10Value, // 미세먼지 pm10 농도
            gradeToStatus(latestItem.pm10Grade),
        )
    }
}