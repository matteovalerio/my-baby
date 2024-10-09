package com.matteo.mybaby2.db

import androidx.room.TypeConverter
import com.matteo.mybaby2.common.schemas.Age

class Converters {
    @TypeConverter
    fun fromAge(value: Long?): Age? {
        return value?.let { it ->
            val years = it / 365
            val months = (it % 365) / 30
            val days = (it % 365) % 30
            Age(years.toInt(), months.toInt(), days.toInt())
        }
    }

    @TypeConverter
    fun toAge(value: Age?): Long? {
        val years = value?.years ?: 0
        val months = value?.months ?: 0
        val days = value?.days ?: 0

        return years * 365L + months * 30L + days
    }

}