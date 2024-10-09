package com.matteo.mybaby2.modules.poopings.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PoopingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val hasPoop: Boolean,
    val hasPiss: Boolean,
    val notes: String,
)
