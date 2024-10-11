package com.matteo.mybaby2.modules.poopings.repositories

import com.matteo.mybaby2.modules.poopings.daos.PoopingDao
import com.matteo.mybaby2.modules.poopings.mappings.PoopingMappings.fromEntity
import com.matteo.mybaby2.modules.poopings.mappings.PoopingMappings.toEntity
import com.matteo.mybaby2.modules.poopings.schemas.PoopingRead
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class PoopingRepository(private val poopingDao: PoopingDao) : IPoopingRepository {
    override suspend fun getAll(): List<PoopingRead> {
        return poopingDao.getAll().map(::fromEntity)
    }

    override suspend fun getAllByDate(date:LocalDate): List<PoopingRead> {
        val zoneId = ZoneId.systemDefault()
        val startOfDay = date.atStartOfDay(zoneId).toInstant().toEpochMilli()
        val endOfDay = date.atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli()
        val poopings = poopingDao.getAllByDate(startOfDay, endOfDay)

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