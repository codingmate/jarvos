package codingmate.jarvos.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime


@Entity
class ParticularMatterInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val createAt: LocalDateTime,

    val stationName: String,

    // 미세먼지 24시간 예측 이동농도
    val pm25Value24H: Int,
    val pm10Value24H: Int,

    // 미세먼지 농도
    val pm25Value: Int,
    val pm10Value: Int,

    // 24시간 미세먼지 등급 (1: 좋음, 2: 보통, 3: 나쁨, 4: 매우나쁨)
    val pm25Grade24H: Int,
    val pm10Grade24H: Int,

    // 1시간 미세먼지 등급 (1: 좋음, 2: 보통, 3: 나쁨, 4: 매우나쁨)
    val pm25Grade1H: Int,
    val pm10Grade1H: Int,
)
