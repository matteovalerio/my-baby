package com.matteo.mybaby2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LabeledText(label: String, text: String, labelStyle: TextStyle? = null, textStyle: TextStyle? = null) {
    Column {
        Text(text = label, style = labelStyle ?: MaterialTheme.typography.labelMedium)
        Text(text = text, style = textStyle ?: MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun LabeledTextPreview() {
    LabeledText(label = "Nome", text = "Alessandro")
}