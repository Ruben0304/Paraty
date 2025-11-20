package com.ruben.paraty.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * Demo de navegación entre las pantallas de autenticación
 */
@Composable
fun AuthenticationDemo(
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(AuthScreen.LOGIN) }
    
    when (currentScreen) {
        AuthScreen.LOGIN -> {
            LoginScreen(
                onNavigateToRegister = { currentScreen = AuthScreen.REGISTER },
                onNavigateToResetPassword = { /* Navegación a reset password */ },
                onLoginSuccess = { /* Éxito del login */ },
                modifier = modifier
            )
        }
        AuthScreen.REGISTER -> {
            RegisterScreen(
                onNavigateToLogin = { currentScreen = AuthScreen.LOGIN },
                onRegisterSuccess = { /* Éxito del registro */ },
                modifier = modifier
            )
        }
    }
}

/**
 * Enumeración para las pantallas de autenticación
 */
enum class AuthScreen {
    LOGIN,
    REGISTER
}
