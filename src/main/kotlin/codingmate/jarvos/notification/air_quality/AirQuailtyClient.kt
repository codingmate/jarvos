package codingmate.jarvos.notification.air_quality


import codingmate.jarvos.notification.air_quality.response.AirQualityResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "airQuality", url = "\${api.airQuality.url}")
interface AirQualityClient {

    @GetMapping("/getMsrstnAcctoRltmMesureDnsty")
    fun getAirQualityData(
        @RequestParam("stationName") stationName: String,
        @RequestParam("serviceKey") serviceKey: String,
        @RequestParam("dataTerm") dataTerm: String = "DAILY", // DAILY(1일), MONTH(1개월), 3MONTH(3개월)
        @RequestParam("pageNo") pageNo: Int = 1,
        @RequestParam("numOfRows") numOfRows: Int = 1,
        @RequestParam("returnType") returnType: String = "json",
        @RequestParam("ver") ver: String = "1.3",
    ): AirQualityResponse

    @GetMapping("/getMsrstnAcctoRltmMesureDnsty")
    fun getLatestData(
        @RequestParam("stationName") stationName: String,
        @RequestParam("serviceKey") serviceKey: String,
        @RequestParam("dataTerm") dataTerm: String = "DAILY", // DAILY(1일), MONTH(1개월), 3MONTH(3개월)
        @RequestParam("pageNo") pageNo: Int = 1,
        @RequestParam("numOfRows") numOfRows: Int = 1,
        @RequestParam("returnType") returnType: String = "json",
        @RequestParam("ver") ver: String = "1.3",
    ): AirQualityResponse
}