package com.matteo.mybaby2.modules.breastfeedings.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreastFeedingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val leftBreastDuration: Float,
    val rightBreastDuration: Float,
    val notes: String,
    val date: Long
)
