package codingmate.jarvos.ollama


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MistralTranslatorServiceTest(
    @Autowired private val service: MistralTranslatorService
) {
    @Test
    fun `한글을 영어로 번역하여 return`() {
        val korean = """
            어머니 존경하고 항상 감사드립니다.
        """.trimIndent()
        val toEnglish = service.koreanToEnglish(korean = korean)
        println(">>>>>>>>>>>>>>>>$toEnglish")
        assertThat(toEnglish).isNotEmpty()
    }

    @Test
    fun `영어을 한글로 번역하여 return`() {
        val english = """
            Mother, I respect and always appreciate you
        """.trimIndent()
        val toKorean = service.englishToKorean(english = english)
        println(">>>>>>>>>>>>>>>>$toKorean")
        assertThat(toKorean).isNotEmpty()
    }
}
