package com.matteo.mybaby2.modules.poopings.mappings

import com.matteo.mybaby2.modules.poopings.entities.PoopingEntity
import com.matteo.mybaby2.modules.poopings.schemas.PoopingRead
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert

object PoopingMappings {
    fun fromEntity(entity: PoopingEntity): PoopingRead {
        return PoopingRead(
            entity.id,
            entity.hasPoop,
            entity.hasPiss,
            entity.notes,
            entity.date
        )
    }

    fun toEntity(schema: PoopingUpsert): PoopingEntity {
        return PoopingEntity(
            id = schema.id ?: 0,
            hasPoop = schema.hasPoop,
            hasPiss = schema.hasPiss,
            notes = schema.notes,
            date = schema.date,
        )
    }
    fun toEntity(schema: PoopingRead): PoopingEntity {
        return PoopingEntity(
            id = schema.id,
            hasPoop = schema.hasPoop,
            hasPiss = schema.hasPiss,
            notes = schema.notes,
            date = schema.date,
        )
    }
}