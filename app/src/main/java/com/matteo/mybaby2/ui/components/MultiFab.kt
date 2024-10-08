package com.matteo.mybaby2.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MultiFab(
    fabIcon: ImageVector,
    fabOptions: List<FabOption>,
    onFabOptionClick: (FabOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        fabOptions.forEachIndexed { index, fabOption ->
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                MiniFab(
                    icon = fabOption.icon,
                    text = fabOption.text,
                    onClick = {
                        onFabOptionClick(fabOption)
                        expanded = false // Close the menu after click
                    },
                    modifier = Modifier.padding(top = (index * 16).dp) // Adjust spacing
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        FloatingActionButton(onClick = { expanded = !expanded }) {
            Icon(fabIcon, contentDescription = "Expand FAB")
        }
    }
}

@Composable
fun MiniFab(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Icon(icon, contentDescription = text)
    }
}

data class FabOption(
    val icon: ImageVector,
    val text: String,
    val index: Int
)