package codingmate.jarvos.notification.telegram.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TelegramBotSendMessageResponse(
    val ok: Boolean,
    val result: Result
) {
    data class Result(
        @JsonProperty("message_id") val messageId: Int,
        val from: User,
        val chat: Chat,
        val date: Long,
        val text: String
    ) {
        data class User(
            val id: Long,
            @JsonProperty("is_bot") val isBot: Boolean,
            @JsonProperty("first_name") val firstName: String,
            val username: String?
        )

        data class Chat(
            val id: Long,
            val title: String?,
            val type: String,
            @JsonProperty("all_members_are_administrators") val allMembersAreAdministrators: Boolean
        )
    }
}