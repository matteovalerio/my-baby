package com.matteo.mybaby2.modules.activities.repositories

import com.matteo.mybaby2.modules.activities.schemas.ActivityRead

interface IActivityRepository {
    suspend fun getAllByBabyId(babyId: Int): List<ActivityRead>
    suspend fun getById(id: Int): ActivityRead
}