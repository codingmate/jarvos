package codingmate.jarvos.notification.telegram.response

import com.fasterxml.jackson.annotation.JsonProperty
data class TelegramGetUpdatesResponse(
    val ok: Boolean,
    val result: List<Update>
) {
    data class Update(
        @JsonProperty("update_id") val updateId: Long,
        @JsonProperty("my_chat_member") val myChatMember: MyChatMember? = null,
        val message: Message? = null
    ) {
        data class Chat(
            val id: Long,
            val title: String,
            val type: String,
            @JsonProperty("all_members_are_administrators") val allMembersAreAdministrators: Boolean
        )
        data class User(
            val id: Long,
            @JsonProperty("is_bot") val isBot: Boolean,
            @JsonProperty("first_name") val firstName: String,
            @JsonProperty("last_name") val lastName: String? = null,
            @JsonProperty("language_code") val languageCode: String? = null
        )
        data class MyChatMember(
            val chat: Chat,
            val from: User,
            val date: Long,
            @JsonProperty("old_chat_member") val oldChatMember: ChatMember,
            @JsonProperty("new_chat_member") val newChatMember: ChatMember
        ) {
            data class ChatMember(
                val user: User,
                val status: String
            )
        }
        data class Message(
            @JsonProperty("message_id") val messageId: Long,
            val from: User,
            val chat: Chat,
            val date: Long,
            @JsonProperty("group_chat_created") val groupChatCreated: Boolean? = null
        )
    }
}