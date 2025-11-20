package com.ruben.paraty

import androidx.compose.ui.window.ComposeUIViewController
import com.ruben.paraty.model.UserType
import com.ruben.paraty.theme.ParatyTheme
import com.ruben.paraty.auth.LoginScreen
import com.ruben.paraty.auth.RegisterScreen

fun HomeViewController(viewModel: HomeViewModel) = ComposeUIViewController {
    ParatyTheme {
        HomeScreen(viewModel)
    }
}

fun SearchViewController() = ComposeUIViewController {
    ParatyTheme {
        SearchScreen()
    }
}

fun AddEventViewController() = ComposeUIViewController {
    ParatyTheme {
        AddEventScreen()
    }
}

fun SettingsViewController() = ComposeUIViewController {
    ParatyTheme {
        SettingsScreen()
    }
}

fun LoginViewController(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onSkip: (UserType) -> Unit
) = ComposeUIViewController {
    ParatyTheme {
        LoginScreen(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToResetPassword = {},
            onLoginSuccess = onLoginSuccess,
            onSkip = onSkip
        )
    }
}

fun RegisterViewController(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) = ComposeUIViewController {
    ParatyTheme {
        RegisterScreen(
            onNavigateToLogin = onNavigateToLogin,
            onRegisterSuccess = onRegisterSuccess
        )
    }
}