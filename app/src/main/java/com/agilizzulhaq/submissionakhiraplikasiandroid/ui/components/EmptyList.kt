package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EmptyList(
    alert: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = alert,
            color = textColor
        )
    }
}