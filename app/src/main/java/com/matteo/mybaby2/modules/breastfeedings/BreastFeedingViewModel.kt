package com.matteo.mybaby2.modules.breastfeedings

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteo.mybaby2.modules.activities.repositories.IActivityRepository
import com.matteo.mybaby2.modules.breastfeedings.repositories.BreastFeedingRepository
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingUpsert
import com.matteo.mybaby2.modules.poopings.PoopUiState
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


sealed class UiState {
    object Loading : UiState()
    object Success: UiState()
    data class Error(val exception: Throwable) : UiState()
}

class BreastFeedingViewModel(
    private val repository: BreastFeedingRepository,
) : ViewModel() {

    var id = mutableStateOf<Int?>(null)
        private set
    var leftBreastDuration = mutableFloatStateOf(0f)
        private set
    var rightBreastDuration = mutableFloatStateOf(0f)
        private set
    var notes = mutableStateOf("")
        private set
    var date = mutableStateOf<Long?>(null)
        private set
    var uiState = mutableStateOf<UiState>(UiState.Success)
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
    fun updateId(id: Int?) {
        this.id.value = id
    }

    fun submit() {
        uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(date.value == null){
                    // TODO dirty hack, add validations!
                    date.value = System.currentTimeMillis()
                }

                repository.upsertBreastFeeding(
                    BreastFeedingUpsert(
                        id = id.value,
                        leftBreastDuration.floatValue,
                        rightBreastDuration.floatValue,
                        notes.value,
                        date.value!!
                    )
                )
                uiState.value = UiState.Success
            } catch (exception: Exception) {
                uiState.value = UiState.Error(exception)
            }
        }
    }

}