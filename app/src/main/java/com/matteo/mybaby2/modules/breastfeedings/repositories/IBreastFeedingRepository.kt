package com.matteo.mybaby2.modules.breastfeedings.repositories

import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingUpsert
import java.time.LocalDate

interface IBreastFeedingRepository {
    suspend fun getAll(): List<BreastFeedingRead>
    suspend fun getAllByDate(date: LocalDate): List<BreastFeedingRead>
    suspend fun upsertBreastFeeding(breastFeeding: BreastFeedingUpsert)
    suspend fun getBreastFeedingById(id: Int): BreastFeedingRead?
}