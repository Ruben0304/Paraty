package com.ruben.paraty

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {
    private val _showMap = MutableStateFlow(true)
    val showMap: StateFlow<Boolean> = _showMap.asStateFlow()

    fun toggleView() {
        _showMap.value = !_showMap.value
    }
}
