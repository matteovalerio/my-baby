package com.matteo.mybaby2.modules.activities.components

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.common.schemas.UiState
import com.matteo.mybaby2.modules.activities.ActivitiesViewModel
import com.matteo.mybaby2.modules.activities.schemas.ActivityRead
import com.matteo.mybaby2.ui.components.FabOption
import com.matteo.mybaby2.ui.components.LabeledText
import com.matteo.mybaby2.ui.components.MultiFab
import org.koin.androidx.compose.koinViewModel


@Composable
fun Activities(
    navController: NavHostController,
    babyId: Int,
    viewModel: ActivitiesViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = babyId) {
        viewModel.getAllActivitiesByBabyId(babyId)
    }

    when (val state = viewModel.allActivitiesUiState.value) {
        is UiState.Error -> Text(text = "Error: ${state.exception.message}") // TODO better error management
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Success<List<ActivityRead>> -> Inner(navController, babyId, state.data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Inner(
    navController: NavHostController,
    babyId: Int,
    activities: List<ActivityRead>,
    modifier: Modifier = Modifier
) {
    return Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.activities),
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }, floatingActionButton = {
        MultiFab(
            fabIcon = Icons.Filled.Add,
            fabOptions = listOf(
                FabOption(
                    icon = Icons.Filled.WaterDrop,
                    text = stringResource(R.string.create_activity)
                )
            ),
            onFabOptionClick = { fabOption ->
                if (fabOption.icon == Icons.Filled.WaterDrop) {
                    navController.navigate("${NavigationItem.Activities.route}/$babyId/breastfeeding/create")
                }
            }
        )
    }) { innerPadding ->
        LazyColumn(modifier = modifier.padding(innerPadding)) {
            items(activities.size) { index ->
                ListItem(headlineContent = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LabeledText(
                                text = activities[index].name,
                                label = stringResource(R.string.name)
                            )
                            LabeledText(
                                text = activities[index].duration.toString(),
                                label = stringResource(R.string.duration)
                            )
                        }
                        HorizontalDivider()
                    }

                })
            }
        }
    }
}