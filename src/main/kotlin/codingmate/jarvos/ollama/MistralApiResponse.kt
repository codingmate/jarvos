package codingmate.jarvos.ollama

//@JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
data class MistralApiResponse(
    val model: String,
    val created_at: String,
    val response: String,
    val done: Boolean,
    val context: List<Int>,
    val total_duration: Long,
    val load_duration: Long,
    val prompt_eval_count: Int,
    val prompt_eval_duration: Long,
    val eval_count: Int,
    val eval_duration: Long
)
