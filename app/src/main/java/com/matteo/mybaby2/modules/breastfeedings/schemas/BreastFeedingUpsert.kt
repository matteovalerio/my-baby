package com.matteo.mybaby2.modules.breastfeedings.schemas

data class BreastFeedingUpsert(
    val id: Int?,
    val leftBreast: Float,
    val rightBreast: Float,
    val notes: String,
    val date: Long
)
