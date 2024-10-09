package com.matteo.mybaby2.db

import android.app.Activity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matteo.mybaby2.modules.babies.entities.BabyEntity
import com.matteo.mybaby2.modules.breastfeedings.daos.BreastFeedingDao
import com.matteo.mybaby2.modules.breastfeedings.entities.BreastFeedingEntity
import com.matteo.mybaby2.modules.poopings.daos.PoopingDao
import com.matteo.mybaby2.modules.poopings.entities.PoopingEntity

@Database(
    entities = [BreastFeedingEntity::class, PoopingEntity::class, BabyEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun poopingDao(): PoopingDao
    abstract fun breastFeedingDao(): BreastFeedingDao
}