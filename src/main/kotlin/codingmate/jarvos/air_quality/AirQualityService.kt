package codingmate.jarvos.air_quality

import codingmate.jarvos.notification.air_quality.AirQualityClient
import codingmate.jarvos.notification.air_quality.response.AirQualityResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AirQualityService(
    private val airQualityClient: AirQualityClient,

    @Value("\${api.airQuality.enCodingKey}")
    val enKey: String
) {
    fun fetchDailyAirQualityByStationName(stationName: String): AirQualityResponse {
        return airQualityClient.getLatestData(stationName = stationName, serviceKey = enKey)
    }
    fun getLatestAirQuality(by: List<AirQualityResponse.Response.Body.Item>): AirQualityResponse.Response.Body.Item {
        if ( by.isEmpty() )
            throw IllegalArgumentException(">>>>> Hourly Air Quality List is Empty")
        return by[0]
    }

    fun fetchLatestHourlyAirQuality(stationName: String): AirQualityResponse.Response.Body.Item {
        val response = fetchDailyAirQualityByStationName(stationName).response
        val hourlyAirQualityList = response.body.items
        val latestHourlyAirQuality = hourlyAirQualityList[0]

        return latestHourlyAirQuality
    }

    fun getAirStatusByGrade(grade: Int): String {
        return when(grade) {
            1 -> "좋음"
            2 -> "보통"
            3 -> "나쁨"
            4 -> "매우나쁨"
            else -> "?"
        }
    }
}