package com.matteo.mybaby2.modules.poopings.components

import android.widget.Toast
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
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.navigations.NavigationItem
import com.matteo.mybaby2.modules.poopings.PoopViewModel
import com.matteo.mybaby2.modules.poopings.schemas.PoopingRead
import com.matteo.mybaby2.ui.components.LabeledText
import com.matteo.mybaby2.ui.components.SwipableBackground
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Poopings(
    modifier: Modifier = Modifier,
    date: Long,
    navController: NavHostController,
    viewModel: PoopViewModel = koinViewModel()
) {
    LaunchedEffect(date) {
        viewModel.getAllPoopingsByDate(date)
    }
    if (viewModel.poopings.value.isEmpty()) {
        return Text(stringResource(R.string.no_data), modifier = modifier)
    }
    return LazyColumn(modifier = modifier) {
        items(viewModel.poopings.value.size) { index ->
            Pooping(
                viewModel.poopings.value[index],
                onRemove = {
                    viewModel.deletePooping(it)
                    viewModel.getAllPoopingsByDate(date)
                },
                onEdit = { navController.navigate("${NavigationItem.Activities.route}/pooping/update/${it.id}") })
        }
    }
}

@Composable
private fun Pooping(
    pooping: PoopingRead,
    onRemove: (item: PoopingRead) -> Unit,
    onEdit: (item: PoopingRead) -> Unit
) {
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onRemove(pooping)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onEdit(pooping)
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
        backgroundContent = { SwipableBackground(dismissState) }) {
        ListItem(headlineContent = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                        Icon(
                            if (pooping.hasPoop) Icons.Filled.Check else Icons.Filled.Close,
                            contentDescription = null
                        )
                        Text(stringResource(R.string.poop))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                        Icon(
                            if (pooping.hasPiss) Icons.Filled.Check else Icons.Filled.Close,
                            contentDescription = null
                        )
                        Text(stringResource(R.string.piss))
                    }

                    val date = Date(pooping.date)
                    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    val formattedDate = formatter.format(date)
                    LabeledText(
                        label = stringResource(R.string.date),
                        text = formattedDate
                    )
                }
                if (pooping.notes.isNotEmpty()) {
                    LabeledText(
                        label = stringResource(R.string.notes),
                        text = pooping.notes
                    )
                }
                HorizontalDivider()
            }
        })
    }
}