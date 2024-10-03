package com.matteo.mybaby2.modules.babies.components

import android.R.attr.label
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.matteo.mybaby2.modules.babies.BabyViewModel
import com.matteo.mybaby2.modules.babies.schemas.BabyRead
import com.matteo.mybaby2.ui.components.LabeledText
import com.matteo.mybaby2.R

@Composable
fun Babies(viewModel: BabyViewModel = viewModel(), modifier: Modifier) {
    val babies = remember { viewModel.babies }
    return LazyColumn(modifier = modifier.padding(10.dp)) {
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