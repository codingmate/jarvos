package codingmate.jarvos.data.entity.history

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
@Entity
class TelegramBotApiUseHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val createAt: LocalDateTime,
    val apiType: String,
    val etc: String? = null,
    val chatId: String? = null,
    val message: String? = null
)
