package com.matteo.mybaby2.modules.babies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.modules.babies.BabyViewModel
import com.matteo.mybaby2.modules.babies.schemas.BabyRead
import com.matteo.mybaby2.ui.components.LabeledText
import org.koin.androidx.compose.koinViewModel

@Composable
fun Babies(navController: NavHostController, viewModel: BabyViewModel = koinViewModel()) {
    val babies = remember { viewModel.babies }
    return LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(babies.value) { baby ->
            Baby(baby)
        }
    }
}

@Composable
private fun Baby(babyRead: BabyRead) {
    return Column {
        ListItem(
            headlineContent = {
                Column {
                    LabeledText(label = stringResource(id = R.string.name), text = babyRead.name)
                    LabeledText(
                        label = stringResource(id = R.string.age),
                        text = babyRead.age.toString()
                    )
                }
            },
        )
        HorizontalDivider()
    }
}