package codingmate.jarvos.notification.air_quality

import codingmate.jarvos.air_quality.AirQualityService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AirQualityTestController(
    val airQualityService: AirQualityService
){
    @GetMapping("/api/pm")
    fun getPmStatus(@RequestParam stationName: String): Map<String, Any> {

        val latestPmStatus = airQualityService.fetchDailyAirQualityByStationName(stationName)

        return mapOf("data" to latestPmStatus)
    }
}