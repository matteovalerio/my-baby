package com.matteo.mybaby2.modules.poopings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matteo.mybaby2.R
import com.matteo.mybaby2.modules.poopings.PoopViewModel
import com.matteo.mybaby2.ui.components.LabeledText
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Poopings(
    modifier: Modifier = Modifier,
    date: Long,
    viewModel: PoopViewModel = koinViewModel()
) {
    LaunchedEffect(date) {
        viewModel.getAllPoopingsByDate(date)
    }
    return LazyColumn(modifier = modifier) {
        items(viewModel.poopings.value.size) { index ->
            val value = viewModel.poopings.value[index]

            ListItem(headlineContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                            Icon(
                                if (value.hasPoop) Icons.Filled.Check else Icons.Filled.Close,
                                contentDescription = null
                            )
                            Text(stringResource(R.string.poop))
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                            Icon(
                                if (value.hasPiss) Icons.Filled.Check else Icons.Filled.Close,
                                contentDescription = null
                            )
                            Text(stringResource(R.string.piss))
                        }

                        val date = Date(value.date)
                        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val formattedDate = formatter.format(date)
                        LabeledText(
                            label = stringResource(R.string.date),
                            text = formattedDate
                        )
                    }
                    if(value.notes.isNotEmpty()) {
                        LabeledText(
                            label = stringResource(R.string.notes),
                            text = value.notes
                        )
                    }
                    HorizontalDivider()
                }
            })
        }
    }
}