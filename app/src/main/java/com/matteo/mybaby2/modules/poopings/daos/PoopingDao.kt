package com.matteo.mybaby2.modules.poopings.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matteo.mybaby2.modules.poopings.entities.PoopingEntity

@Dao
interface PoopingDao {
    @Query("SELECT * FROM poopingentity")
    suspend fun getAll(): List<PoopingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPooping(pooping: PoopingEntity)

    @Query("SELECT * FROM poopingentity WHERE id = :id")
    suspend fun getPoopingById(id: Int): PoopingEntity?

    @Query("SELECT * FROM poopingentity WHERE date BETWEEN :startOfDay AND :endOfDay")
    suspend fun getAllByDate(startOfDay: Long, endOfDay: Long): List<PoopingEntity>
}