package codingmate.jarvos.notification.telegram.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

import jakarta.persistence.Id



@Entity
class TelegramMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    val chatId: Long,
    val message: String
){
    constructor() : this(0, 0, "")
    constructor(chatId: Long, message: String) : this(0, chatId, message)
}