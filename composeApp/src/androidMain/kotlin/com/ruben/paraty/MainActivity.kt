package com.ruben.paraty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.ruben.paraty.theme.ParatyTheme
import com.ruben.paraty.auth.LoginScreen
import com.ruben.paraty.auth.RegisterScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParatyTheme {
                // Estado de autenticación
                var isAuthenticated by remember { mutableStateOf(false) }
                
                if (!isAuthenticated) {
                    // Flujo de autenticación
                    var showRegister by remember { mutableStateOf(false) }
                    
                    if (showRegister) {
                        RegisterScreen(
                            onNavigateToLogin = { showRegister = false },
                            onRegisterSuccess = { isAuthenticated = true },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        LoginScreen(
                            onNavigateToRegister = { showRegister = true },
                            onNavigateToResetPassword = { /* TODO: Implementar */ },
                            onLoginSuccess = { isAuthenticated = true },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    // Aplicación principal con navegación
                    val navController = rememberNavController()
                    val homeViewModel = remember { HomeViewModel() }
                    val showMap by homeViewModel.showMap.collectAsState()

                    Scaffold {
                        Box(modifier = Modifier.fillMaxSize()) {
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.fillMaxSize()
                            ) {
                                composable("home") { HomeScreen(homeViewModel) }
                                composable("search") { SearchScreen() }
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
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Inicio") },
                                    selected = false, // Logic to be added
                                    onClick = { navController.navigate("home") }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                                    label = { Text("Buscar") },
                                    selected = false,
                                    onClick = { navController.navigate("search") }
                                )
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
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