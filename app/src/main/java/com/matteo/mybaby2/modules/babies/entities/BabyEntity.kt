package com.matteo.mybaby2.modules.babies.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matteo.mybaby2.common.schemas.Age


@Entity
data class BabyEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String, val age: Age)