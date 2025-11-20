package com.ruben.paraty

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

@Composable
fun MapScreen() {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            NativeLoadingIndicator(modifier = Modifier.size(100.dp))
        } else {
            MapComponent(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
