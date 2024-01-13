package codingmate.jarvos.notification.air_quality

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AirQualityRestController(
    val airQualityService: AirQualityService
){
    @GetMapping("/api/pm")
    fun getPmStatus(@RequestParam stationName: String): Map<String, Any> {

        val latestPmStatus = airQualityService.getLatestPmStatus(stationName)

        return mapOf("data" to latestPmStatus)
    }
}