package com.ruben.paraty

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun NativeLoadingIndicator(modifier: Modifier) {
    LoadingIndicator(modifier = modifier)
}
