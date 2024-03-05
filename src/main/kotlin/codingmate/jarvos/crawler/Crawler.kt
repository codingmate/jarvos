package codingmate.jarvos.crawler

import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class Crawler {
    fun fetchRobotsTxt(hostUrl: String): String {
        val client = OkHttpClient()
        val req = Request.Builder()
            .url(hostUrl)
            .build()

        client.newCall(req).execute().use { res ->
            if(!res.isSuccessful) throw IOException("""
                url: $hostUrl
                failed response
            """.trimIndent())

            return res.body?.string() ?: throw IOException("""
                url: $hostUrl
                Response body is null
            """.trimIndent()
            )
        }
    }
}