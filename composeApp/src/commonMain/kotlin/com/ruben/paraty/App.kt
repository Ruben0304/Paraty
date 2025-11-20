package com.ruben.paraty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.ruben.paraty.theme.ParatyTheme
import com.ruben.paraty.auth.LoginScreen
import com.ruben.paraty.auth.RegisterScreen

/**
 * Estado de autenticación de la aplicación
 */
enum class AuthState {
    LOGIN,
    REGISTER,
    AUTHENTICATED
}

@Composable
@Preview
fun App() {
    // Estado de autenticación - inicialmente en LOGIN
    var authState by remember { mutableStateOf(AuthState.LOGIN) }
    
    ParatyTheme {
        when (authState) {
            AuthState.LOGIN -> {
                LoginScreen(
                    onNavigateToRegister = { authState = AuthState.REGISTER },
                    onNavigateToResetPassword = { /* TODO: Implementar reset password */ },
                    onLoginSuccess = { authState = AuthState.AUTHENTICATED },
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            AuthState.REGISTER -> {
                RegisterScreen(
                    onNavigateToLogin = { authState = AuthState.LOGIN },
                    onRegisterSuccess = { authState = AuthState.AUTHENTICATED },
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            AuthState.AUTHENTICATED -> {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .safeContentPadding()
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    MapScreen()
                }
            }
        }
    }
}