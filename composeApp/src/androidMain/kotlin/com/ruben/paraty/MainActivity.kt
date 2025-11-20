package com.ruben.paraty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruben.paraty.model.UserType
import com.ruben.paraty.theme.ParatyTheme
import com.ruben.paraty.auth.LoginScreen
import com.ruben.paraty.auth.RegisterScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParatyTheme {
                // AuthViewModel
                val authViewModel = remember { AuthViewModel() }
                val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
                val userType by authViewModel.userType.collectAsState()

                if (!isAuthenticated) {
                    // Flujo de autenticación
                    var showRegister by remember { mutableStateOf(false) }

                    if (showRegister) {
                        RegisterScreen(
                            onNavigateToLogin = { showRegister = false },
                            onRegisterSuccess = {
                                authViewModel.register(UserType.CLIENT)
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        LoginScreen(
                            onNavigateToRegister = { showRegister = true },
                            onNavigateToResetPassword = { /* TODO: Implementar */ },
                            onLoginSuccess = {
                                authViewModel.login(UserType.CLIENT)
                            },
                            onSkip = { selectedUserType ->
                                authViewModel.skip(selectedUserType)
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    // Aplicación principal con navegación dinámica según tipo de usuario
                    val navController = rememberNavController()
                    val homeViewModel = remember { HomeViewModel() }

                    Scaffold {
                        Box(modifier = Modifier.fillMaxSize()) {
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.fillMaxSize()
                            ) {
                                composable("home") { HomeScreen(homeViewModel) }
                                composable("search") { SearchScreen() }
                                composable("addEvent") { AddEventScreen() }
                                composable("settings") { SettingsScreen() }
                            }

                            NavigationBar(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(horizontal = 64.dp, vertical = 16.dp)
                                    .clip(RoundedCornerShape(50)),
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                tonalElevation = 8.dp
                            ) {
                                // Tab Inicio (siempre presente)
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                                    label = { Text("Inicio") },
                                    selected = false,
                                    onClick = { navController.navigate("home") }
                                )

                                // Tab del medio - cambia según tipo de usuario
                                if (userType == UserType.BUSINESS) {
                                    // Para negocios: botón de agregar evento
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Add, contentDescription = "Agregar") },
                                        label = { Text("Agregar") },
                                        selected = false,
                                        onClick = { navController.navigate("addEvent") }
                                    )
                                } else {
                                    // Para clientes: botón de buscar
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                                        label = { Text("Buscar") },
                                        selected = false,
                                        onClick = { navController.navigate("search") }
                                    )
                                }

                                // Tab Ajustes (siempre presente)
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                                    label = { Text("Ajustes") },
                                    selected = false,
                                    onClick = { navController.navigate("settings") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}