package com.matteo.mybaby2.modules.poopings.repositories

import com.matteo.mybaby2.modules.poopings.daos.PoopingDao
import com.matteo.mybaby2.modules.poopings.mappings.PoopingMappings.fromEntity
import com.matteo.mybaby2.modules.poopings.mappings.PoopingMappings.toEntity
import com.matteo.mybaby2.modules.poopings.schemas.PoopingRead
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert

class PoopingRepository(private val poopingDao: PoopingDao) : IPoopingRepository {
    override suspend fun getAll(): List<PoopingRead> {
        val poopings = poopingDao.getAll()

        return poopings.map(::fromEntity)
    }

    override suspend fun upsertPooping(pooping: PoopingUpsert) {
        poopingDao.insertPooping(toEntity(pooping))
    }

    override suspend fun getPoopingById(id: Int): PoopingRead? {
        val pooping = poopingDao.getPoopingById(id)
        return pooping?.let(::fromEntity)
    }
}