package com.matteo.mybaby2.modules.breastfeedings.mappings

import com.matteo.mybaby2.modules.breastfeedings.entities.BreastFeedingEntity
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingUpsert

object BreastFeedingMappings {
    fun fromEntity(entity: BreastFeedingEntity): BreastFeedingRead = BreastFeedingRead(
        id = entity.id,
        leftBreast = entity.leftBreastDuration,
        rightBreast = entity.rightBreastDuration,
        notes = entity.notes,
        date = entity.date
    )
    fun toEntity(schema: BreastFeedingUpsert): BreastFeedingEntity = BreastFeedingEntity(
        id = schema.id ?: 0,
        leftBreastDuration = schema.leftBreast,
        rightBreastDuration = schema.rightBreast,
        notes = schema.notes,
        date = schema.date
    )
}