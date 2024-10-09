package com.matteo.mybaby2.modules.activities.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.breastfeedings.components.BreastFeedings
import com.matteo.mybaby2.modules.poopings.components.Poopings
import com.matteo.mybaby2.ui.components.FabOption
import com.matteo.mybaby2.ui.components.MultiFab


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activities(
    navController: NavHostController,
) {
    var selectedItem = remember { mutableIntStateOf(0) }
    val items = listOf(stringResource(R.string.breastfeeding), stringResource(R.string.pooping),stringResource(R.string.weight))
    val selectedIcons = listOf(Icons.Filled.Fastfood, Icons.Filled.Wc, Icons.Filled.MonitorWeight)
    val unselectedIcons =
        listOf(Icons.Outlined.Fastfood, Icons.Outlined.Wc, Icons.Outlined.MonitorWeight)


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
                            if (selectedItem.intValue == index) selectedIcons[index] else unselectedIcons[index],
                            contentDescription = item
                        )
                    },
                    label = { Text(item) },
                    selected = selectedItem.intValue == index,
                    onClick = { selectedItem.intValue = index }
                )
            }
        }
    }) { innerPadding ->
        if(selectedItem.intValue == 0){
            BreastFeedings(Modifier.padding(innerPadding),1)
        }
        if(selectedItem.intValue == 1){
            Poopings(Modifier.padding(innerPadding),1)
        }
    }
}