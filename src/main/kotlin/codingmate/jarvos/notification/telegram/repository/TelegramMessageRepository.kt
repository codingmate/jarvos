package codingmate.jarvos.notification.telegram.repository

import codingmate.jarvos.notification.telegram.entity.TelegramMessage
import org.springframework.data.jpa.repository.JpaRepository

interface TelegramMessageRepository: JpaRepository<TelegramMessage, Long>
