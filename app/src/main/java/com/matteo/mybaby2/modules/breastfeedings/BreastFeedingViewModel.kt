package com.matteo.mybaby2.modules.breastfeedings

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.matteo.mybaby2.modules.activities.repositories.IActivityRepository
import kotlin.math.roundToInt


class BreastFeedingViewModel(
    private val activityRepository: IActivityRepository,
) : ViewModel() {

    var leftBreastDuration = mutableFloatStateOf(0f)
        private set

    var rightBreastDuration = mutableFloatStateOf(0f)
        private set
    var notes = mutableStateOf("")
        private set
    var date = mutableStateOf<Long?>(null)
        private set

    fun updateLeftBreastDuration(duration: Float) {
        this.leftBreastDuration.floatValue = duration.roundToInt().toFloat()
    }

    fun updateRightBreastDuration(duration: Float) {
        this.rightBreastDuration.floatValue = duration.roundToInt().toFloat()
    }

    fun updateNotes(notes: String) {
        this.notes.value = notes
    }

    fun updateDate(date: Long?) {
        this.date.value = date
    }

    fun submit() {
        /*TODO*/
    }

}