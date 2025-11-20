package com.ruben.paraty

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun SFSymbolIcon(
    symbolName: String,
    contentDescription: String?,
    modifier: Modifier
) {
    val imageVector = when (symbolName) {
        "map" -> Icons.Default.Map
        "list.bullet" -> Icons.Default.List
        else -> Icons.Default.Map
    }
    
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

