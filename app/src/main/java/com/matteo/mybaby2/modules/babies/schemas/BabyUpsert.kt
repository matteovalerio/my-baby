package com.matteo.mybaby2.modules.babies.schemas

import com.matteo.mybaby2.common.schemas.Age

data class BabyUpsert(val name: String, val age: Age)
