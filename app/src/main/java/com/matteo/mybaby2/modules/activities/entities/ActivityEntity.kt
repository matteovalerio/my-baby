package com.matteo.mybaby2.modules.activities.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matteo.mybaby2.modules.breastfeedings.entities.BreastFeedingEntity
import com.matteo.mybaby2.modules.poopings.entities.PoopingEntity

@Entity
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Embedded val breastFeeding: BreastFeedingEntity?,
    @Embedded val pooping: PoopingEntity?,
)