package codingmate.jarvos.crawler

import org.jsoup.Jsoup
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.Duration

@Component
class Selenium(
    private val driver: WebDriver
) {
    private fun <T> processTemplate(url: String, process:(WebDriver) -> T): T {
        try {
            driver.get(url)
            return process(driver)
        } finally {
            driver.quit()
        }
    }
    // String HTML 가져오기
    fun getHtmlByUrl(url: String) = processTemplate(url) { driver.pageSource }

    // Document 얻기
    fun getDocumentByUrl(url: String) = processTemplate(url) { Jsoup.parse(driver.pageSource) }

    // 특정 요소의 텍스트 추출
    fun getTextBySelector(url: String, cssSelector: String) = processTemplate(url) {
        it.findElement(By.cssSelector(cssSelector)).text
    }

    // 모든 링크 추출
    fun getAllLinks(url: String) = processTemplate(url) {
        it.findElements(By.tagName("a")).mapNotNull { element -> element.getAttribute("href") }.distinct()
    }

    // 페이지 스크린샷 캡쳐
    fun captureScreenshot(url: String, filePath: String) = processTemplate(url) {
        val file = (it as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        Files.copy(file.toPath(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING)
    }

    // Element 로딩까지 대기
    fun waitForElement(url: String, cssSelector: String, timeoutInSeconds: Long) = processTemplate(url) {
        WebDriverWait(it, Duration.ofSeconds(timeoutInSeconds))
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)))
    }
}