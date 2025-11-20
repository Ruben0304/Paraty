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
 * Pantalla de registro con diseño inspirado en Doctolib
 * Incluye opción para seleccionar tipo de cuenta (Personal o Negocio)
 */
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isBusinessAccount by remember { mutableStateOf(false) }
    var businessName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    
    // Función de registro
    fun performRegister() {
        when {
            fullName.isBlank() -> {
                errorMessage = "Por favor ingresa tu nombre completo"
            }
            email.isBlank() -> {
                errorMessage = "Por favor ingresa tu email"
            }
            !email.contains("@") -> {
                errorMessage = "Email inválido"
            }
            password.isBlank() -> {
                errorMessage = "Por favor ingresa una contraseña"
            }
            password.length < 6 -> {
                errorMessage = "La contraseña debe tener al menos 6 caracteres"
            }
            password != confirmPassword -> {
                errorMessage = "Las contraseñas no coinciden"
            }
            isBusinessAccount && businessName.isBlank() -> {
                errorMessage = "Por favor ingresa el nombre de tu negocio"
            }
            else -> {
                errorMessage = null
                isLoading = true
                // Aquí iría tu lógica de registro
                // Por ahora llamamos directamente a onRegisterSuccess
                onRegisterSuccess()
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
                    AccountCircleIcon(
                        modifier = Modifier.size(48.dp),
                        tint = ParatyBlue
                    )
                }
            )
            
            // Título
            Text(
                text = "Crear cuenta",
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
                text = "Únete a la comunidad de Paraty",
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
            
            // Selector de tipo de cuenta
            AccountTypeSelector(
                isBusinessAccount = isBusinessAccount,
                onAccountTypeChange = { 
                    isBusinessAccount = it
                    errorMessage = null
                },
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Campo de nombre completo
            AuthTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    errorMessage = null
                },
                placeholder = "Nombre completo",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    PersonIcon(
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF9E9E9E)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Campo de nombre del negocio (solo si es cuenta de negocio)
            if (isBusinessAccount) {
                AuthTextField(
                    value = businessName,
                    onValueChange = {
                        businessName = it
                        errorMessage = null
                    },
                    placeholder = "Nombre del negocio",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    leadingIcon = {
                        ShoppingCartIcon(
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF9E9E9E)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
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
                placeholder = "Confirmar contraseña",
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { 
                        focusManager.clearFocus()
                        performRegister()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botón de registro
            AuthButton(
                text = "Crear cuenta",
                onClick = ::performRegister,
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Términos y condiciones
            Text(
                text = "Al crear una cuenta, aceptas nuestros Términos y Condiciones",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(24.dp))
            
            // Link para iniciar sesión
            TextWithLink(
                normalText = "¿Ya tienes cuenta?",
                linkText = "Inicia sesión",
                onLinkClick = onNavigateToLogin
            )
        }
    }
}
