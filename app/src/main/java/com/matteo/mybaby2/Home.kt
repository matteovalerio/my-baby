package com.matteo.mybaby2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.matteo.mybaby2.common.converters.DateConverters
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.breastfeedings.components.BreastFeedings
import com.matteo.mybaby2.modules.poopings.components.Poopings
import com.matteo.mybaby2.modules.statistics.components.Statistics
import com.matteo.mybaby2.ui.components.DatePickerModal
import com.matteo.mybaby2.ui.components.FabOption
import com.matteo.mybaby2.ui.components.MultiFab
import java.time.Instant
import java.time.ZoneId


enum class Tabs {
    BreastFeeding, Pooping, Graphs
}


@Composable
private fun getItemName(tab: Tabs): String {
    return when (tab) {
        Tabs.BreastFeeding -> stringResource(R.string.breastfeeding)
        Tabs.Pooping -> stringResource(R.string.pooping)
        Tabs.Graphs -> stringResource(R.string.graphs)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController,
    date: Long? = null,
    tab: Int? = null
) {
    var selectedItem =
        rememberSaveable { mutableStateOf(if (tab != null) Tabs.entries[tab] else Tabs.BreastFeeding) }
    val items = listOf(Tabs.BreastFeeding, Tabs.Pooping, Tabs.Graphs)
    val selectedIcons = listOf(Icons.Filled.Fastfood, Icons.Filled.Wc, Icons.Filled.BarChart)
    val unselectedIcons =
        listOf(Icons.Outlined.Fastfood, Icons.Outlined.Wc, Icons.Outlined.BarChart)
    val selectedDate = remember { mutableLongStateOf(date ?: Instant.now().toEpochMilli()) }
    val isSelectingDate = remember { mutableStateOf(false) }

    return Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = getItemName(selectedItem.value),
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }, floatingActionButton = {
        if (selectedItem.value != Tabs.Graphs) {
            MultiFab(
                fabIcon = Icons.Filled.Add,
                fabOptions = listOf(
                    FabOption(
                        icon = Icons.Filled.Fastfood,
                        text = stringResource(R.string.create_activity),
                        index = 1
                    ),
                    FabOption(
                        icon = Icons.Filled.Wc,
                        text = stringResource(R.string.create_activity),
                        index = 2
                    )
                ),
                onFabOptionClick = { fabOption ->
                    if (fabOption.index == 1) {
                        navController.navigate("${NavigationItem.Activities.route}/breastfeeding/create")
                    } else if (fabOption.index == 2) {
                        navController.navigate("${NavigationItem.Activities.route}/pooping/create")
                    }
                }
            )
        }
    }, bottomBar = {

        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            if (selectedItem.value == item) selectedIcons[index] else unselectedIcons[index],
                            contentDescription = getItemName(item)
                        )
                    },
                    label = { Text(getItemName(item)) },
                    selected = selectedItem.value == item,
                    onClick = { selectedItem.value = item }
                )
            }
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selectedItem.value != Tabs.Graphs) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        val date = Instant.ofEpochMilli(selectedDate.longValue)
                            .atZone(ZoneId.systemDefault()).toLocalDate()
                        selectedDate.longValue =
                            date.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                .toEpochMilli()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = { isSelectingDate.value = true }) {
                        Text(
                            text = DateConverters.formatMillisToDate(
                                selectedDate.longValue
                            )
                        )
                        if (isSelectingDate.value) {
                            DatePickerModal(
                                onDateSelected = { date ->
                                    if (date != null) {
                                        selectedDate.longValue = date
                                    }
                                },
                                onDismiss = { isSelectingDate.value = false },
                                initialValue = selectedDate.longValue
                            )
                        }

                    }
                    IconButton(onClick = {
                        val date = Instant.ofEpochMilli(selectedDate.longValue)
                            .atZone(ZoneId.systemDefault()).toLocalDate()
                        selectedDate.longValue =
                            date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                .toEpochMilli()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = "Forward"
                        )
                    }
                }
            }
            HorizontalDivider()
            when (selectedItem.value) {
                Tabs.BreastFeeding -> BreastFeedings(
                    date = selectedDate.longValue,
                    navController = navController
                )

                Tabs.Pooping -> Poopings(
                    date = selectedDate.longValue,
                    navController = navController
                )

                Tabs.Graphs -> Statistics()
            }
        }
    }
}