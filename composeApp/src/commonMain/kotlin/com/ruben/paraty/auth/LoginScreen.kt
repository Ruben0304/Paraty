package com.ruben.paraty.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ruben.paraty.model.UserType
import com.ruben.paraty.theme.ParatyBlue

/**
 * Pantalla de inicio de sesión con diseño inspirado en Doctolib
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onNavigateToResetPassword: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onSkip: (UserType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSkipDialog by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    
    // Función de login
    fun performLogin() {
        when {
            email.isBlank() -> {
                errorMessage = "Por favor ingresa tu email"
            }
            password.isBlank() -> {
                errorMessage = "Por favor ingresa tu contraseña"
            }
            !email.contains("@") -> {
                errorMessage = "Email inválido"
            }
            else -> {
                errorMessage = null
                isLoading = true
                // Aquí iría tu lógica de autenticación
                // Por ahora llamamos directamente a onLoginSuccess
                onLoginSuccess()
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE8EBFA)) // Fondo azul lavanda suave como Doctolib
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            ParatyLogo(
                modifier = Modifier.padding(bottom = 40.dp)
            )
            
            // Ícono decorativo
            AuthIcon(
                modifier = Modifier.padding(bottom = 24.dp),
                icon = {
                    CheckIcon(
                        modifier = Modifier.size(48.dp),
                        tint = ParatyBlue
                    )
                }
            )
            
            // Título
            Text(
                text = "Iniciar sesión",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF1F1F1F)
                ),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Subtítulo
            Text(
                text = "Ingresa a tu cuenta de Paraty",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color(0xFF5F6368),
                    textAlign = TextAlign.Center
                )
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Mensaje de error
            errorMessage?.let { message ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = Color(0xFFFFEBEE),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = message,
                        modifier = Modifier.padding(12.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFFC62828)
                        )
                    )
                }
            }
            
            // Campo de email
            AuthTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = null
                },
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    EmailIcon(
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF9E9E9E)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Campo de contraseña
            AuthTextField(
                value = password,
                onValueChange = { 
                    password = it
                    errorMessage = null
                },
                placeholder = "Contraseña",
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { 
                        focusManager.clearFocus()
                        performLogin()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botón de login
            AuthButton(
                text = "Iniciar sesión",
                onClick = ::performLogin,
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Link para olvidé mi contraseña
            TextWithLink(
                normalText = "¿Olvidaste tu contraseña?",
                linkText = "Restablecer",
                onLinkClick = onNavigateToResetPassword
            )
            
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(24.dp))

            // Link para omitir
            TextWithLink(
                normalText = "",
                linkText = "Omitir",
                onLinkClick = { showSkipDialog = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Link para registrarse
            TextWithLink(
                normalText = "¿No tienes cuenta?",
                linkText = "Regístrate",
                onLinkClick = onNavigateToRegister
            )
        }
    }

    // Diálogo para seleccionar tipo de usuario al omitir
    if (showSkipDialog) {
        UserTypeSelectionDialog(
            onDismiss = { showSkipDialog = false },
            onUserTypeSelected = { userType ->
                showSkipDialog = false
                onSkip(userType)
            }
        )
    }
}

/**
 * Diálogo para seleccionar tipo de usuario (Cliente o Negocio)
 */
@Composable
fun UserTypeSelectionDialog(
    onDismiss: () -> Unit,
    onUserTypeSelected: (UserType) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selecciona tu tipo de cuenta",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1F1F1F)
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Opción Cliente
                UserTypeOption(
                    title = "Cliente",
                    description = "Busca y asiste a eventos",
                    icon = {
                        PersonIcon(
                            modifier = Modifier.size(32.dp),
                            tint = ParatyBlue
                        )
                    },
                    onClick = { onUserTypeSelected(UserType.CLIENT) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Opción Negocio
                UserTypeOption(
                    title = "Negocio",
                    description = "Crea y gestiona eventos",
                    icon = {
                        ShoppingCartIcon(
                            modifier = Modifier.size(32.dp),
                            tint = ParatyBlue
                        )
                    },
                    onClick = { onUserTypeSelected(UserType.BUSINESS) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Cancelar",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF5F6368)
                        )
                    )
                }
            }
        }
    }
}

/**
 * Opción de tipo de usuario en el diálogo
 */
@Composable
private fun UserTypeOption(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = ParatyBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1F1F1F)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = Color(0xFF5F6368)
                    )
                )
            }
        }
    }
}
