package com.matteo.mybaby2.modules.poopings.repositories

import com.matteo.mybaby2.modules.poopings.schemas.PoopingRead
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert
import java.time.LocalDate

interface IPoopingRepository {
    suspend fun getAll(): List<PoopingRead>
    suspend fun getAllByDate(date: LocalDate): List<PoopingRead>
    suspend fun upsertPooping(pooping: PoopingUpsert)
    suspend fun getPoopingById(id: Int): PoopingRead?
}