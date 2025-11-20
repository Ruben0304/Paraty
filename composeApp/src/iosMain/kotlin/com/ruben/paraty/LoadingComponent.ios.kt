package com.ruben.paraty

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NativeLoadingIndicator(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}
