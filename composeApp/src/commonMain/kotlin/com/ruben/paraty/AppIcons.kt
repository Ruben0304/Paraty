package com.ruben.paraty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun SFSymbolIcon(
    symbolName: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
)

