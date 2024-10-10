package com.matteo.mybaby2.modules.activities.components

import DateTimePickerModal
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.converters.DateConverters
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.breastfeedings.components.BreastFeedings
import com.matteo.mybaby2.modules.poopings.components.Poopings
import com.matteo.mybaby2.ui.components.DatePickerModal
import com.matteo.mybaby2.ui.components.FabOption
import com.matteo.mybaby2.ui.components.MultiFab
import java.time.Instant


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
fun Activities(
    navController: NavHostController,
) {
    var selectedItem = rememberSaveable { mutableStateOf(Tabs.BreastFeeding) }
    val items = listOf(Tabs.BreastFeeding, Tabs.Pooping, Tabs.Graphs)
    val selectedIcons = listOf(Icons.Filled.Fastfood, Icons.Filled.Wc, Icons.Filled.BarChart)
    val unselectedIcons =
        listOf(Icons.Outlined.Fastfood, Icons.Outlined.Wc, Icons.Outlined.BarChart)
    val selectedDate = remember { mutableLongStateOf(Instant.now().toEpochMilli()) }
    val isSelectingDate = remember { mutableStateOf(false) }

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
        Column (
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            if(selectedItem.value!= Tabs.Graphs) {
                TextButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally),
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
            }
            when (selectedItem.value) {
                Tabs.BreastFeeding -> BreastFeedings(date = selectedDate.longValue)

                Tabs.Pooping -> Poopings(date = selectedDate.longValue)

                Tabs.Graphs -> Placeholder(
                    width = TextUnit(100f, TextUnitType.Sp),
                    height = TextUnit(100f, TextUnitType.Sp),
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            }
        }
    }
}