package com.matteo.mybaby2.modules.breastfeedings

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteo.mybaby2.modules.breastfeedings.repositories.IBreastFeedingRepository
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingUpsert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import kotlin.math.roundToInt


sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val exception: Throwable) : UiState()
}

class BreastFeedingViewModel(
    private val repository: IBreastFeedingRepository,
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
    var uiState = mutableStateOf<UiState>(UiState.Loading)
        private set
    var breastFeedings = mutableStateOf<List<BreastFeedingRead>>(emptyList())

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
                if (date.value == null) {
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

    fun getAllBreastFeedingsByDate(date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val localeDate =
                    Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()
                val result = repository.getAllByDate(localeDate)
                breastFeedings.value = result
            } catch (exception: Exception) {
                // TODO handle error
            }
        }
    }

    fun deleteBreastfeeding(breastFeedingRead: BreastFeedingRead) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.delete(breastFeedingRead)
            } catch (exception: Exception) {
                // TODO handle error
            }
        }
    }

    fun patchForm(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState.value = UiState.Loading
                val value = repository.getById(id)
                if (value != null) {
                    leftBreastDuration.floatValue = value.leftBreast
                    rightBreastDuration.floatValue = value.rightBreast
                    notes.value = value.notes
                    date.value = value.date
                    this@BreastFeedingViewModel.id.value = value.id
                }
                uiState.value = UiState.Success
            } catch (exception: Exception) {
                // TODO handle error
                uiState.value = UiState.Error(exception)
            }
        }
    }

}