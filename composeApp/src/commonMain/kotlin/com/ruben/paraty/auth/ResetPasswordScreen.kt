package com.ruben.paraty.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.ruben.paraty.theme.ParatyBlue

/**
 * Pantalla de restablecimiento de contraseña con diseño Doctolib
 * Replica exactamente el diseño de la imagen proporcionada
 */
@Composable
fun ResetPasswordScreen(
    onNavigateToLogin: () -> Unit = {},
    onResetSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    
    // Función de reset
    fun performReset() {
        when {
            newPassword.isBlank() -> {
                errorMessage = "Por favor ingresa una nueva contraseña"
            }
            newPassword.length < 6 -> {
                errorMessage = "La contraseña debe tener al menos 6 caracteres"
            }
            confirmPassword.isBlank() -> {
                errorMessage = "Por favor confirma tu contraseña"
            }
            newPassword != confirmPassword -> {
                errorMessage = "Las contraseñas no coinciden"
            }
            else -> {
                errorMessage = null
                isLoading = true
                // Aquí iría tu lógica de reset
                // Por ahora llamamos directamente a onResetSuccess
                onResetSuccess()
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
            
            // Ícono decorativo con checkmark (como en el diseño)
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
                text = "Reset password",
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
                text = "Create your Paraty Account Password",
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
            
            // Campo de nueva contraseña
            AuthTextField(
                value = newPassword,
                onValueChange = { 
                    newPassword = it
                    errorMessage = null
                },
                placeholder = "New password",
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Campo de confirmar contraseña
            AuthTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    errorMessage = null
                },
                placeholder = "Confirm password",
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { 
                        focusManager.clearFocus()
                        performReset()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botón de login (como en el diseño original)
            AuthButton(
                text = "Login",
                onClick = ::performReset,
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Link "Know your password? Log in"
            TextWithLink(
                normalText = "Know your password?",
                linkText = "Log in",
                onLinkClick = onNavigateToLogin
            )
        }
    }
}
