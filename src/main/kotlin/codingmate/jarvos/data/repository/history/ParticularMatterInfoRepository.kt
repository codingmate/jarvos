package codingmate.jarvos.data.repository.history

import codingmate.jarvos.data.entity.ParticularMatterInfo
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class ParticularMatterInfoRepository(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun findLatest(): ParticularMatterInfo? {
        val query = entityManager.createQuery(
            "SELECT p FROM ParticularMatterInfo p ORDER BY p.createAt DESC",
            ParticularMatterInfo::class.java
        )
        query.maxResults = 1
        return query.resultList.firstOrNull()
    }
    @Transactional
    fun save(info: ParticularMatterInfo): ParticularMatterInfo {
        entityManager.persist(info)
        return info
    }

    fun findById(id: Long): ParticularMatterInfo? {
        return entityManager.find(ParticularMatterInfo::class.java, id)
    }

    @Transactional
    fun update(info: ParticularMatterInfo): ParticularMatterInfo {
        return entityManager.merge(info)
    }

    @Transactional
    fun delete(id: Long) {
        findById(id)?.let {
            entityManager.remove(it)
        }
    }
}
