package com.matteo.mybaby2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LabeledText(label: String, text: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun LabeledTextPreview() {
    LabeledText(label = "Nome", text = "Alessandro")
}