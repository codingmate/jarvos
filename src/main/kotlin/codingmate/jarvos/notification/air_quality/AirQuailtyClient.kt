package codingmate.jarvos.notification.air_quality


import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "airQualityClient", url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc")
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
    ): ApiResponse

    @GetMapping("/getMsrstnAcctoRltmMesureDnsty")
    fun getLatestData(
        @RequestParam("stationName") stationName: String,
        @RequestParam("serviceKey") serviceKey: String,
        @RequestParam("dataTerm") dataTerm: String = "DAILY", // DAILY(1일), MONTH(1개월), 3MONTH(3개월)
        @RequestParam("pageNo") pageNo: Int = 1,
        @RequestParam("numOfRows") numOfRows: Int = 1,
        @RequestParam("returnType") returnType: String = "json",
        @RequestParam("ver") ver: String = "1.3",
    ): ApiResponse
}

data class ApiResponse(
    val response: Response
)

data class Response(
    val body: Body,
    val header: Header
)

data class Body(
    val totalCount: Int, // 전체 결과 수
    val items: List<Item>, // 측정 데이터 목록
    val pageNo: Int, // 페이지 번호
    val numOfRows: Int // 한 페이지당 결과 수
)

data class Item(
    val so2Grade: String?, // 아황산가스 지수
    val coFlag: String?, // 일산화탄소 플래그
    val khaiValue: String?, // 통합대기환경수치
    val so2Value: String?, // 아황산가스 농도(ppm)
    val coValue: String?, // 일산화탄소 농도(ppm)
    val o3Grade: String?, // 오존 지수
    val khaiGrade: String?, // 통합대기환경지수
    val no2Flag: String?, // 이산화질소 플래그
    val no2Grade: String?, // 이산화질소 지수
    val o3Flag: String?, // 오존 플래그
    val so2Flag: String?, // 아황산가스 플래그

    val coGrade: String?, // 일산화탄소 지수
    val no2Value: String?, // 이산화질소 농도(ppm)
    val o3Value: String?, // 오존 농도(ppm)
    val mangName: String?, // 측정망 정보

    val pm10Value24: String?, // 미세먼지(PM10) 24시간 예측 이동농도(㎍/㎥)
    val pm10Flag: String?, // 미세먼지(PM10) 플래그
    val pm25Grade: String?, // 미세먼지(PM2.5) 24시간 등급자료
    val pm25Flag: String?, // 미세먼지(PM2.5) 플래그
    val pm25Value24: String?, // 미세먼지(PM2.5) 24시간 예측 이동농도(㎍/㎥)

    val dataTime: String, // 측정일시 (연-월-일 시간:분)
    val pm10Value: String, // 미세먼지(PM10) 농도(㎍/㎥)
    val pm10Grade: String, // 미세먼지(PM10) 24시간 등급자료
    val pm25Grade1h: String, // 미세먼지(PM2.5) 1시간 등급자료
    val pm25Value: String?, // 미세먼지(PM2.5) 농도(㎍/㎥)
)

data class Header(
    val resultMsg: String, // 결과메시지
    val resultCode: String // 결과코드
)
