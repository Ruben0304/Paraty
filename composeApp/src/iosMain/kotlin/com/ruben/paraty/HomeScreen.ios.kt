package com.ruben.paraty

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformFloatingActionButton(
    showMap: Boolean,
    onToggle: () -> Unit
) {
    // No mostrar FAB en iOS - usa el toolbar nativo
}
