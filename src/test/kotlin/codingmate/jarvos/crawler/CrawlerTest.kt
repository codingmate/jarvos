package codingmate.jarvos.crawler

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.io.IOException

class CrawlerTest {
    @Test
    fun `엔카 RobotsTxt 확인`() {
        // Given
        val crawler = Crawler()
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
        val crawler = Crawler()
        val hostUrl = "http://doesnotexist.com/robots.txt"

        // Then
        assertThatThrownBy { crawler.fetchRobotsTxt(hostUrl) }
            .isInstanceOf(IOException::class.java)
            .hasMessageContaining("url: $hostUrl")
    }

}