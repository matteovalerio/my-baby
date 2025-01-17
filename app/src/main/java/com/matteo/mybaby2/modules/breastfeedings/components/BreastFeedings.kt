package com.matteo.mybaby2.modules.breastfeedings.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import com.matteo.mybaby2.modules.breastfeedings.schemas.BreastFeedingRead
import com.matteo.mybaby2.ui.components.LabeledText
import com.matteo.mybaby2.ui.components.SwipableBackground
import kotlinx.coroutines.runBlocking
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
    LaunchedEffect(date, viewModel.breastFeedings.value.size) {
        viewModel.getAllBreastFeedingsByDate(date)
    }
    if (viewModel.breastFeedings.value.isEmpty()) {
        return Text(stringResource(R.string.no_data), modifier = modifier)
    }

    return Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val leftBreastFeedings = viewModel.breastFeedings.value.filter { it.leftBreast > 0 }
        val totalLeftBreast = leftBreastFeedings.sumOf { it.leftBreast.toInt() }
        val rightBreastFeedings = viewModel.breastFeedings.value.filter { it.rightBreast > 0 }
        val totalRightBreast = rightBreastFeedings.sumOf { it.rightBreast.toInt() }
        Text(
            stringResource(
                R.string.left_breast,
                totalLeftBreast
            ),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            stringResource(
                R.string.right_breast,
                totalRightBreast
            ),
            style = MaterialTheme.typography.bodySmall,
        )

        LazyColumn {
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
}

@Composable
private fun BreastFeeding(
    breastFeeding: BreastFeedingRead,
    onRemove: (item: BreastFeedingRead) -> Unit,
    onEdit: (item: BreastFeedingRead) -> Unit
) {
    val context = LocalContext.current
    val isDialogOpen = remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    isDialogOpen.value = true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onEdit(breastFeeding)
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState true
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f },
    )

    return SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { SwipableBackground(dismissState) },
        content = {
            ListItem(headlineContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val date = Date(breastFeeding.date)
                    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val formattedDate = formatter.format(date)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "time",
                            modifier = Modifier.fillMaxWidth(.05f)
                        )
                        Text(text = formattedDate, style = MaterialTheme.typography.bodySmall)
                    }
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

                        LabeledText(
                            label = stringResource(R.string.right),
                            text = "${breastFeeding.rightBreast.toInt()} ${
                                stringResource(
                                    R.string.minutes
                                )
                            }"
                        )
                        Spacer(Modifier)
                    }


                    if (breastFeeding.notes.isNotEmpty()) {
                        LabeledText(
                            label = stringResource(R.string.notes),
                            text = breastFeeding.notes
                        )
                    }
                    HorizontalDivider()
                    if (isDialogOpen.value) {
                        val resetDismissState = {
                            runBlocking { dismissState.snapTo(SwipeToDismissBoxValue.Settled) }
                        }
                        AlertDialog(
                            onDismissRequest = {
                                isDialogOpen.value = false
                                resetDismissState()
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    onRemove(breastFeeding)
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT)
                                        .show()
                                    isDialogOpen.value = false
                                    resetDismissState()
                                }) { Text(stringResource(R.string.delete)) }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    isDialogOpen.value = false
                                    resetDismissState()
                                }) { Text(stringResource(R.string.cancel)) }
                            },
                            title = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_item)) })
                    }
                }
            })
        })
}