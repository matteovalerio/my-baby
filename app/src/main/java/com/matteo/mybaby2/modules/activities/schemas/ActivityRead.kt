package com.matteo.mybaby2.modules.activities.schemas

import com.matteo.mybaby2.modules.babies.schemas.BabyRead
import java.time.Instant
import kotlin.time.Duration

data class ActivityRead(
    val id: Int,
    val name: String,
    val baby: BabyRead,
    val breastfeedingActivities: BreastfeedingActivitiesRead,
    val duration: Duration,
    val createdAt: Instant
)

data class BreastfeedingActivitiesRead(
    val leftBreast: BreastfeedingActivity,
    val rightBreast: BreastfeedingActivity,
)

data class BreastfeedingActivity(val duration: Duration)
