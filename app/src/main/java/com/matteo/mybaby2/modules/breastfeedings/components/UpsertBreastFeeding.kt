package com.matteo.mybaby2.modules.breastfeedings.components

import DatePickerModal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.converters.DateConverters
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingUpsert
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertBreastFeeding(
    navHostController: NavHostController,
    babyId: Int,
    viewModel: BreastFeedingViewModel = koinViewModel(),
    defaultValues: BreastFeedingUpsert? = null
) {
    if(defaultValues != null) {
        viewModel.updateLeftBreastDuration(defaultValues.leftBreast.toFloat())
        viewModel.updateRightBreastDuration(defaultValues.rightBreast.toFloat())
        viewModel.updateNotes(defaultValues.notes)
        viewModel.updateDate(defaultValues.date)
        viewModel.updateId(defaultValues.id)
    }
    var showDatePicker = remember { mutableStateOf(false) }
    return Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.add_breastfeeding)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = if (viewModel.date.value == null) "" else DateConverters.convertMillisToDate(
                        viewModel.date.value!!
                    ),
                    onValueChange = { },
                    label = { Text(text = stringResource(R.string.date)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker.value = !showDatePicker.value }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
                if (showDatePicker.value) {
                    DatePickerModal(
                        onDateSelected = { date -> viewModel.updateDate(date) },
                        onDismiss = { showDatePicker.value = false })
                }

                Column {
                    Text(
                        text = "${stringResource(R.string.left)} - ${stringResource(R.string.minutes)} ${viewModel.leftBreastDuration.floatValue.toInt()}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Slider(
                        value = viewModel.leftBreastDuration.floatValue,
                        onValueChange = viewModel::updateLeftBreastDuration,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.secondary,
                            activeTrackColor = MaterialTheme.colorScheme.secondary,
                            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        valueRange = 1f..60f
                    )
                }
                Column {
                    Text(
                        text = "${stringResource(R.string.right)} - ${stringResource(R.string.minutes)} ${viewModel.rightBreastDuration.floatValue.toInt()}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Slider(
                        value = viewModel.rightBreastDuration.floatValue,
                        onValueChange = viewModel::updateRightBreastDuration,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.secondary,
                            activeTrackColor = MaterialTheme.colorScheme.secondary,
                            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        valueRange = 1f..60f
                    )
                }

                OutlinedTextField(
                    viewModel.notes.value,
                    onValueChange = viewModel::updateNotes,
                    minLines = 3,
                    label = {
                        Text(
                            text = stringResource(R.string.notes),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )

            }
            Button(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    viewModel.submit()
                    navHostController.navigate("${NavigationItem.Activities.route}/${babyId}")
                }) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}