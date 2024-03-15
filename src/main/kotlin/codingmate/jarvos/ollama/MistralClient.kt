package codingmate.jarvos.ollama

import org.springframework.cloud.openfeign.FeignClient

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(name = "mistralClient", url = "\${api.ollama.url}")
interface MistralClient {
    @PostMapping("/generate")
    fun generate(
        @RequestBody generateRequest: MistralGenerateRequest
    ): MistralApiResponse
}

