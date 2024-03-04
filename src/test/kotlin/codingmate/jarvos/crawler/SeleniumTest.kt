package codingmate.jarvos.crawler

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith( // Junit5의 테스트 클래스/메소드에 확장 기능 적용
    MockitoExtension::class // Mockito를 사용하여 목 객체를 초기화하고 관리하는 기능 제공. 이 확장을 사용함으로써, 테스트 내에서 @Mock이나 @InjectMocks 어노테이션 등을 사용하여 목 객체를 쉽게 생성하고 주입받기 가능
    , SpringExtension::class // Spring TestContext Framework와의 통합 제공. 이 확장을 사용함으로써, Spring의 의존성 주입, @SpringBootTest 등의 Spring 테스트 관련 어노테이션을 JUnit 5 테스트에서 사용 가능
)
class SeleniumTest {

    private lateinit var mockDriver: WebDriver // WebDriver 인터페이스의 목 객체 선언
    private lateinit var selenium: Selenium // 테스트 대상 클래스 인스턴스 선언

    @BeforeEach
    fun setUp() {
        mockDriver = mock(WebDriver::class.java) // 목 객체 초기화
        selenium = Selenium(mockDriver) // 목 객체를 사용하여 Selenium 인스턴스 생성
    }

    @Test
    fun `test getHtmlByUrl`() {
        // Given: WebDriver의 pageSource가 특정 HTML을 반환하도록 설정
        `when`(mockDriver.pageSource).thenReturn("<html></html>")
        // When: getHtmlByUrl 메소드 호출
        val result = selenium.getHtmlByUrl("http://example.com")
        // Then: 반환된 HTML 문자열 검증
        assertThat(result).isEqualTo("<html></html>")
    }

    @Test
    fun `test getDocumentByUrl`() {
        // Given: WebDriver의 pageSource가 특정 HTML을 반환하도록 설정
        `when`(mockDriver.pageSource).thenReturn("<html><head><title>Test Title</title></head></html>")
        // When: getDocumentByUrl 메소드 호출
        val document = selenium.getDocumentByUrl("http://example.com")
        // Then: 반환된 Document 객체의 타이틀 검증
        assertThat(document.title()).isEqualTo("Test Title")
    }

    @Test
    fun `test getTextBySelector`() {
        // Given: WebDriver의 findElement 메소드가 특정 WebElement 목 객체를 반환하도록 설정
        val mockElement = mock(WebElement::class.java)
        `when`(mockDriver.findElement(any())).thenReturn(mockElement)
        // WebElement의 text 메소드가 "Mocked Text"를 반환하도록 설정
        `when`(mockElement.text).thenReturn("Mocked Text")
        // When: getTextBySelector 메소드 호출
        val text = selenium.getTextBySelector("http://example.com", "#selector")
        // Then: 반환된 텍스트 검증
        assertThat(text).isEqualTo("Mocked Text")
    }

    @Test
    fun `test getAllLinks`() {
        // Given: WebDriver의 findElements 메소드가 WebElement 목 객체 리스트를 반환하도록 설정
        val mockElement = mock(WebElement::class.java)
        `when`(mockDriver.findElements(any())).thenReturn(listOf(mockElement))
        // WebElement의 getAttribute 메소드가 "http://example.com"을 반환하도록 설정ㄴ
        `when`(mockElement.getAttribute("href")).thenReturn("http://example.com")
        // When: getAllLinks 메소드 호출
        val links = selenium.getAllLinks("http://example.com")
        // Then: 반환된 링크 리스트의 크기 검증
        assertThat(links).hasSize(1)
    }

}