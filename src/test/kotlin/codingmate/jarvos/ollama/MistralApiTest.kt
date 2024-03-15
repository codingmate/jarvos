package codingmate.jarvos.ollama

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MistralApiTest {
    @Test
    fun `generate API test`() {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = """
            {
              "model": "mistral:7b",
              "prompt": "[어머니 사랑합니다. 아들이 항상 감사하고, 존경하고 있습니다.] 영어로 번역",
              "stream": false
            }
        """.trimIndent().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("http://localhost:11434/api/generate")
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        println(">>>>>>>>>>>> $request")
        assertThat(response.isSuccessful).isTrue
        val responseBody = response.body?.string()
        val mapper = jacksonObjectMapper()

        if ( responseBody != null ) {
            val apiResponse = mapper.readValue(responseBody, MistralApiResponse::class.java)
            println(">>>>>>>>>>>> ${apiResponse}")
        }

        // assertThat(responseBody).contains("The sky is blue because")
    }
}