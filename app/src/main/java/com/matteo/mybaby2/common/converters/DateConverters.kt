package com.matteo.mybaby2.common.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverters {
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}