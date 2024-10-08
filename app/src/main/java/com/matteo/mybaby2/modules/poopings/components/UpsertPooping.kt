package com.matteo.mybaby2.modules.poopings.components

import DatePickerModal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.matteo.mybaby2.modules.poopings.PoopViewModel
import org.koin.androidx.compose.koinViewModel
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.converters.DateConverters
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert


// TODO add default values
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertPooping(
    navController: NavController,
    babyId: Int,
    viewModel: PoopViewModel = koinViewModel(),
    defaultValues: PoopingUpsert? = null
) {
    if(defaultValues!= null){
        viewModel.updateHasPoop(defaultValues.hasPoop)
        viewModel.updateHasPiss(defaultValues.hasPiss)
        viewModel.updateNotes(defaultValues.notes)
        viewModel.updateDate(defaultValues.date)
    }

    var showDatePicker = remember { mutableStateOf(false) }

    return Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.add_change_diapers)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }) { innerPadding ->
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    Column {
                        Text(
                            text = stringResource(R.string.poop),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Switch(viewModel.hasPoop.value, onCheckedChange = viewModel::updateHasPoop)
                    }
                    Column {
                        Text(
                            text = stringResource(R.string.piss),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Switch(viewModel.hasPiss.value, onCheckedChange = viewModel::updateHasPoop)
                    }
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
                    navController.navigate("${NavigationItem.Activities.route}/${babyId}")
                }) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}