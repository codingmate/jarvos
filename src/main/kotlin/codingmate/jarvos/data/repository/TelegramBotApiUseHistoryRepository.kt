package codingmate.jarvos.data.repository

import codingmate.jarvos.data.entity.history.TelegramBotApiUseHistory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository


@Repository
class TelegramBotApiUseHistoryRepository(
    @PersistenceContext private val entityManager: EntityManager
) {

    @Transactional
    fun save(history: TelegramBotApiUseHistory): TelegramBotApiUseHistory {
        entityManager.persist(history)
        return history
    }

    fun findById(id: Long): TelegramBotApiUseHistory? {
        return entityManager.find(TelegramBotApiUseHistory::class.java, id)
    }

    @Transactional
    fun update(history: TelegramBotApiUseHistory): TelegramBotApiUseHistory {
        return entityManager.merge(history)
    }

    @Transactional
    fun delete(id: Long) {
        findById(id)?.let {
            entityManager.remove(it)
        }
    }
}
