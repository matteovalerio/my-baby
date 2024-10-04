package com.matteo.mybaby2.modules.activities

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteo.mybaby2.common.schemas.UiState
import com.matteo.mybaby2.modules.activities.repositories.IActivityRepository
import com.matteo.mybaby2.modules.activities.schemas.ActivityRead
import kotlinx.coroutines.launch


class ActivityViewModel(private val activityRepository: IActivityRepository) : ViewModel() {
    var allActivitiesUiState = mutableStateOf<UiState<List<ActivityRead>>>(UiState.Loading)
        private set
    var activityUiState = mutableStateOf<UiState<ActivityRead>>(UiState.Loading)

    fun getAllActivitiesByBabyId(babyId: Int) {
        viewModelScope.launch {
            allActivitiesUiState.value = UiState.Loading
            allActivitiesUiState.value =
                try {
                    UiState.Success(activityRepository.getAllByBabyId(babyId))
                } catch (e: Exception) {
                    UiState.Error(e)

                }
        }
    }

    fun getActivityById(id: Int) {
        viewModelScope.launch {
            activityUiState.value = UiState.Loading
            activityUiState.value =
                try {
                    UiState.Success(activityRepository.getById(id))
                } catch (e: Exception) {
                    UiState.Error(e)

                }
        }
    }


}