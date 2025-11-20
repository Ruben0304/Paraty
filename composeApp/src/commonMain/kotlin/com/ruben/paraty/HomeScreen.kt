package com.ruben.paraty

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val showMap by viewModel.showMap.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (showMap) {
            MapScreen()
        } else {
            ListScreen()
        }
        
        // Floating Action Button solo en Android (iOS usa toolbar nativo)
        PlatformFloatingActionButton(
            showMap = showMap,
            onToggle = { viewModel.toggleView() }
        )
    }
}

@Composable
expect fun PlatformFloatingActionButton(
    showMap: Boolean,
    onToggle: () -> Unit
)
