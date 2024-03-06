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
    fun extractAllowDisallowUrlsFromRobotsTxt(robotsTxt: String): Pair<Set<String>, Set<String>> {
        val allowUrlSet = mutableSetOf<String>()
        val disallowUrlSet = mutableSetOf<String>()

        robotsTxt.lineSequence().forEach { line ->
            when {
                line.startsWith("Allow: ") -> allowUrlSet.add(line.removePrefix("Allow: ").trim())
                line.startsWith("Disallow: ") -> disallowUrlSet.add(line.removePrefix("Disallow: ").trim())
            }
        }

        return Pair(allowUrlSet, disallowUrlSet)
    }
}