package com.matteo.mybaby2.modules.breastfeedings.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.ui.components.SwipableBackground
import com.matteo.mybaby2.ui.components.LabeledText
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BreastFeedings(
    modifier: Modifier = Modifier,
    date: Long,
    navController: NavHostController,
    viewModel: BreastFeedingViewModel = koinViewModel()
    ) {
    LaunchedEffect(date) {
        viewModel.getAllBreastFeedingsByDate(date)
    }
    if (viewModel.breastFeedings.value.isEmpty()) {
        return Text(stringResource(R.string.no_data), modifier = modifier)
    }

    return LazyColumn(modifier = modifier) {
        items(viewModel.breastFeedings.value.size) { index ->
            BreastFeeding(
                viewModel.breastFeedings.value[index],
                onRemove = {
                    viewModel.deleteBreastfeeding(it)
                    viewModel.getAllBreastFeedingsByDate(date)
                },
                onEdit = { navController.navigate("${NavigationItem.Activities.route}/breastfeeding/update/${it.id}") }
            )
        }
    }
}

@Composable
private fun BreastFeeding(breastFeeding: BreastFeedingRead, onRemove: (item: BreastFeedingRead) -> Unit, onEdit: (item: BreastFeedingRead) -> Unit) {
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onRemove(breastFeeding)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onEdit(breastFeeding)
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )

    return SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { SwipableBackground(dismissState) },
        content = {
            ListItem(headlineContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LabeledText(
                            label = stringResource(R.string.left),
                            text = "${breastFeeding.leftBreast.toInt()} ${
                                stringResource(
                                    R.string.minutes
                                )
                            }"
                        )

                        val date = Date(breastFeeding.date)
                        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val formattedDate = formatter.format(date)
                        LabeledText(
                            label = stringResource(R.string.date),
                            text = formattedDate
                        )
                    }
                    LabeledText(
                        label = stringResource(R.string.right),
                        text = "${breastFeeding.rightBreast.toInt()} ${
                            stringResource(
                                R.string.minutes
                            )
                        }"
                    )

                    if (breastFeeding.notes.isNotEmpty()) {
                        LabeledText(
                            label = stringResource(R.string.notes),
                            text = breastFeeding.notes
                        )
                    }
                    HorizontalDivider()
                }
            })
        })
}