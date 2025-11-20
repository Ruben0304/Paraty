package com.ruben.paraty

import com.ruben.paraty.model.UserType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel global para manejar el estado de autenticación
 */
class AuthViewModel {
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _userType = MutableStateFlow<UserType?>(null)
    val userType: StateFlow<UserType?> = _userType.asStateFlow()

    /**
     * Login exitoso
     */
    fun login(userType: UserType = UserType.CLIENT) {
        _isAuthenticated.value = true
        _userType.value = userType
    }

    /**
     * Registro exitoso
     */
    fun register(userType: UserType) {
        _isAuthenticated.value = true
        _userType.value = userType
    }

    /**
     * Omitir autenticación con tipo de usuario seleccionado
     */
    fun skip(userType: UserType) {
        _isAuthenticated.value = true
        _userType.value = userType
    }

    /**
     * Logout
     */
    fun logout() {
        _isAuthenticated.value = false
        _userType.value = null
    }
}
