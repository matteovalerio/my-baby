package com.matteo.mybaby2.modules.activities.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.matteo.mybaby2.common.schemas.UiState
import com.matteo.mybaby2.modules.activities.ActivityViewModel
import com.matteo.mybaby2.modules.activities.schemas.ActivityRead
import org.koin.androidx.compose.koinViewModel

@Composable
fun Activities(babyId: Int,viewModel: ActivityViewModel = koinViewModel()){
    LaunchedEffect(key1 = babyId) {
        viewModel.getAllActivitiesByBabyId(babyId)
    }

    when (val state = viewModel.allActivitiesUiState.value) {
        is UiState.Error -> Text(text = "Error: ${state.exception.message}") // TODO better error management
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Success<List<ActivityRead>> -> TODO()
    }
}