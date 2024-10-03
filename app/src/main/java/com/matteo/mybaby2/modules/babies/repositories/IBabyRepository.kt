package com.matteo.mybaby2.modules.babies.repositories

import com.matteo.mybaby2.modules.babies.schemas.BabyUpsert
import com.matteo.mybaby2.modules.babies.schemas.BabyRead

interface IBabyRepository {
    fun getAll(): List<BabyRead>
    fun getById(id: Int): BabyRead
    fun create(dto: BabyUpsert)
    fun update(id: String,dto: BabyUpsert)
    fun delete(id: String)
}