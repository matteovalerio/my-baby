package com.matteo.mybaby2.modules.poopings.schemas

data class PoopingUpsert(val hasPoop: Boolean, val hasPiss: Boolean, val notes: String, val date: Long)
