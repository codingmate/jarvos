package codingmate.jarvos.data.repository.history

import codingmate.jarvos.data.entity.history.TelegramBotApiUseHistory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class TelegramBotApiUseHistoryRepository(
    @PersistenceContext private val entityManager: EntityManager
) {

    fun findLatestToday(): TelegramBotApiUseHistory? {
        val todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
        val todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999)

        val query = entityManager.createQuery(
                "SELECT h FROM TelegramBotApiUseHistory h WHERE h.createAt BETWEEN :start AND :end ORDER BY h.createAt DESC",
                TelegramBotApiUseHistory::class.java
        )
        query.setParameter("start", todayStart)
        query.setParameter("end", todayEnd)
        query.maxResults = 1

        return query.resultList.firstOrNull()
    }
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
