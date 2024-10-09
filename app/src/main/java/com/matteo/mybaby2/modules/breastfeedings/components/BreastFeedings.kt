package com.matteo.mybaby2.modules.breastfeedings.components

import android.util.Log
import android.util.Log.d
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import com.matteo.mybaby2.ui.components.LabeledText
import org.koin.androidx.compose.koinViewModel
import java.util.Date
import com.matteo.mybaby2.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BreastFeedings(
    modifier: Modifier,
    day: Int,
    viewModel: BreastFeedingViewModel = koinViewModel()
) {
    LaunchedEffect(viewModel.breastFeedings.value) {
        viewModel.getAllBreastFeedings()
    }
    return LazyColumn(modifier = modifier) {
        items(viewModel.breastFeedings.value.size) { index ->
            ListItem(headlineContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LabeledText(
                            label = stringResource(R.string.left),
                            text = "${viewModel.breastFeedings.value[index].leftBreast.toInt()} ${
                                stringResource(
                                    R.string.minutes
                                )
                            }"
                        )

                        val date = Date(viewModel.breastFeedings.value[index].date)
                        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val formattedDate = formatter.format(date)
                        LabeledText(
                            label = stringResource(R.string.date),
                            text = formattedDate
                        )
                    }
                    LabeledText(
                        label = stringResource(R.string.right),
                        text = "${viewModel.breastFeedings.value[index].rightBreast.toInt()} ${
                            stringResource(
                                R.string.minutes
                            )
                        }"
                    )

                    if(viewModel.breastFeedings.value[index].notes.isNotEmpty()) {
                        LabeledText(
                            label = stringResource(R.string.notes),
                            text = viewModel.breastFeedings.value[index].notes
                        )
                    }
                    HorizontalDivider()
                }
            })
        }
    }
}