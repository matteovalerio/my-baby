package com.matteo.mybaby2.modules.statistics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteo.mybaby2.modules.breastfeedings.repositories.IBreastFeedingRepository
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.modules.poopings.repositories.IPoopingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZoneOffset

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val exception: Throwable) : UiState()
}

data class StatisticRead(val date: Long, val amount: Int)

class StatisticsViewModel(
    private val breastFeedingRepository: IBreastFeedingRepository,
    private val poopingRepository: IPoopingRepository
) :
    ViewModel() {
    var breastFeedings = mutableStateOf<List<StatisticRead>>(emptyList())
        private set
    var breastFeedingMinutes = mutableStateOf<List<StatisticRead>>(emptyList())
        private set
    var diaperChanges = mutableStateOf<List<StatisticRead>>(emptyList())
        private set
    var uiState = mutableStateOf<UiState>(UiState.Loading)

    fun getAllBreastFeedings() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value = UiState.Loading
            try {
                val breastFeedings = breastFeedingRepository.getAll()
                this@StatisticsViewModel.breastFeedings.value =
                    breastFeedings.groupBy { breastFeeding ->
                        Instant.ofEpochMilli(breastFeeding.date)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }.map { (date, breastFeedingsForDay) ->

                        StatisticRead(
                            date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                            breastFeedingsForDay.size
                        )
                    }.sortedBy { it.date }
                this@StatisticsViewModel.breastFeedingMinutes.value =
                    breastFeedings.groupBy { breastFeeding ->
                        Instant.ofEpochMilli(breastFeeding.date)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }.map { (date, breastFeedingsForDay) ->

                        StatisticRead(
                            date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                            breastFeedingsForDay.sumOf { it.rightBreast.toInt() } + breastFeedingsForDay.sumOf { it.leftBreast.toInt() }
                        )
                    }.sortedBy { it.date }
                uiState.value = UiState.Success
            } catch (exception: Exception) {
                uiState.value = UiState.Error(exception) // TODO better error handling
            }
        }
    }

    fun getAllPoopingsAndPissings() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value = UiState.Loading
            try {
                val poopingsAndPissings = poopingRepository.getAll()
                diaperChanges.value =
                    poopingsAndPissings
                        .groupBy { pooping ->
                            Instant.ofEpochMilli(pooping.date)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }.map { (date, poopingForDay) ->

                            StatisticRead(
                                date.atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli(),
                                poopingForDay.size
                            )
                        }.sortedBy { it.date }
                uiState.value = UiState.Success
            } catch (exception: Exception) {
                uiState.value = UiState.Error(exception) // TODO better error handling
            }
        }
    }
}