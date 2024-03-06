package codingmate.jarvos.crawler

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class CrawlerTest {

    private lateinit var crawler: Crawler

    @BeforeEach
    fun setUp() {
        crawler = Crawler()
    }
    @Test
    fun `엔카 RobotsTxt 확인`() {
        // Given
        val hostUrl = "http://www.encar.com/robots.txt"

        // When
        val result = crawler.fetchRobotsTxt(hostUrl)

        // Then
        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
    }

    @Test
    fun `fetchRobotsTxt should throw IOException when request is unsuccessful`() {
        // Given
        val hostUrl = "http://doesnotexist.com/robots.txt"

        // Then
        assertThatThrownBy { crawler.fetchRobotsTxt(hostUrl) }
            .isInstanceOf(IOException::class.java)
    }

    @Test
    fun `parseRobotsTxt should correctly parse allow and disallow urls`() {
        // Given
        val robotsTxt = """
            User-agent: *
            Mozilla/5.0 (compatible; Yeti/1.1; +http://help.naver.com/robots/)
            Allow: /
            Disallow: /ac/
            Disallow: /my/
            Disallow: /cm/
            Disallow: /board/
        """.trimIndent()

        val (allowUrls, disallowUrls) = crawler.extractAllowDisallowUrlsFromRobotsTxt(robotsTxt)

        assertThat(allowUrls).containsExactly("/")
        assertThat(disallowUrls).containsExactlyInAnyOrder("/ac/", "/my/", "/cm/", "/board/")
    }
}