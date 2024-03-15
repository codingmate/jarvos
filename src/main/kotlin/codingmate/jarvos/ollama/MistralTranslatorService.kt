package codingmate.jarvos.ollama

import org.springframework.stereotype.Service

@Service
class MistralTranslatorService(
    private val mistralClient: MistralClient
) {
    fun koreanToEnglish(korean: String): String {
        val prompt = """
            Translate literally, without paraphrasing
            Translate the Korean characters inside the brackets ([]) into English
            [$korean]
        """.trimIndent()
        return mistralClient.generate(MistralGenerateRequest(prompt = prompt)).response
    }
    fun englishToKorean(english: String): String {
        val prompt = """
            Translate literally, without paraphrasing
            Translate the English characters inside the brackets ([]) into Korean
            [$english]
        """.trimIndent()
        return mistralClient.generate(MistralGenerateRequest(prompt = prompt)).response
    }
}