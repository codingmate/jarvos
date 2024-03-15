package codingmate.jarvos.ollama

data class MistralGenerateRequest(
    val model: String = "mistral:7b",
    val prompt: String,
    val stream: Boolean = false
)