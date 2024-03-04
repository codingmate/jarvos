package codingmate.jarvos.crawler

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SeleniumConfig {

    @Value("\${webdriver.chrome.path}")
    lateinit var chromeDriverPath: String

    @Bean
    fun chromeDriver(): WebDriver {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("--headless")
        return ChromeDriver(chromeOptions)
    }
}