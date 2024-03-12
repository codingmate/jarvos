package codingmate.jarvos.crawler.news

import java.time.LocalDateTime

data class Article(
    val source: String, // 신문사명 또는 출처
    val title: String, // 기사 제목
    val summary: String, // 기사 요약
    val publishedDateTime: LocalDateTime, // 발행 날짜
    val url: String, // 기사 링크
    val author: String, // 작성자
)

