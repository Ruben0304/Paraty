package com.ruben.paraty

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

@Composable
actual fun PlatformFloatingActionButton(
    showMap: Boolean,
    onToggle: () -> Unit
) {
    // Mostrar FAB en Android
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        FloatingActionButton(
            onClick = onToggle,
            modifier = Modifier
                .safeContentPadding()
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            SFSymbolIcon(
                symbolName = if (showMap) "list.bullet" else "map",
                contentDescription = if (showMap) "Ver lista" else "Ver mapa"
            )
        }
    }
}
