package com.matteo.mybaby2.modules.breastfeedings.repositories

import com.matteo.mybaby2.modules.breastfeedings.daos.BreastFeedingDao
import com.matteo.mybaby2.modules.breastfeedings.mappings.BreastFeedingMappings.fromEntity
import com.matteo.mybaby2.modules.breastfeedings.mappings.BreastFeedingMappings.toEntity
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingUpsert
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date

class BreastFeedingRepository(private val breastFeedingDao: BreastFeedingDao): IBreastFeedingRepository {
    override suspend fun getAll(): List<BreastFeedingRead> {
        val breastFeedings = breastFeedingDao.getAll()
        return breastFeedings.map(::fromEntity)
    }

    override suspend fun getAllByDate(date: LocalDate): List<BreastFeedingRead> {
        val zoneId = ZoneId.systemDefault()
        val startOfDay = date.atStartOfDay(zoneId).toInstant().toEpochMilli()
        val endOfDay = date.atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli()
        val breastFeedings = breastFeedingDao.getAllByDate(startOfDay, endOfDay)
        return breastFeedings.map(::fromEntity)
    }

    override suspend fun getById(id: Int): BreastFeedingRead? {
        return breastFeedingDao.getBreastFeedingById(id)?.let(::fromEntity)
    }

    override suspend fun upsertBreastFeeding(breastFeeding: BreastFeedingUpsert) {
        breastFeedingDao.upsertBreastFeeding(toEntity(breastFeeding))
    }

    override suspend fun getBreastFeedingById(id: Int): BreastFeedingRead? {
        return breastFeedingDao.getBreastFeedingById(id)?.let(::fromEntity)
    }

    override suspend fun delete(breastFeeding: BreastFeedingRead) {
        breastFeedingDao.delete(toEntity(breastFeeding))
    }
}