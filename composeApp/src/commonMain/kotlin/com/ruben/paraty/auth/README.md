# Pantallas de Autenticación - Paraty

Este paquete contiene las pantallas de autenticación con un diseño moderno inspirado en Doctolib, implementadas en Jetpack Compose Multiplatform.

## 📱 Pantallas Incluidas

### 1. **LoginScreen** (`LoginScreen.kt`)
Pantalla de inicio de sesión con:
- Campo de email
- Campo de contraseña con toggle de visibilidad
- Botón de inicio de sesión
- Link para recuperar contraseña
- Link para ir a registro

### 2. **RegisterScreen** (`RegisterScreen.kt`)
Pantalla de registro con:
- Selector de tipo de cuenta (Personal / Negocio)
- Campo de nombre completo
- Campo de nombre del negocio (solo si es cuenta de negocio)
- Campo de email
- Campos de contraseña y confirmación
- Botón de crear cuenta
- Link para ir a login

### 3. **ResetPasswordScreen** (`ResetPasswordScreen.kt`)
Pantalla de restablecimiento de contraseña con:
- Campos para nueva contraseña y confirmación
- Botón de login
- Link para volver a login

### 4. **AuthComponents** (`AuthComponents.kt`)
Componentes reutilizables:
- `ParatyLogo` - Logo de la aplicación
- `AuthIcon` - Ícono decorativo animado
- `AuthTextField` - Campo de texto personalizado
- `AuthButton` - Botón principal
- `AccountTypeSelector` - Selector Personal/Negocio
- `TextWithLink` - Texto con enlace clicable

## 🎨 Diseño

El diseño replica el estilo limpio y moderno de Doctolib con:
- **Fondo**: Azul lavanda suave (#E8EBFA)
- **Color primario**: Azul Paraty (#4D71DC)
- **Campos de texto**: Blancos con bordes redondeados (28dp)
- **Botones**: Azul con bordes redondeados (28dp)
- **Tipografía**: Sans-serif moderna con jerarquía clara
- **Espaciado**: Generoso para una apariencia limpia y premium

## 🚀 Uso Básico

### Opción 1: Usar AuthenticationDemo (Recomendado para probar)

```kotlin
import com.ruben.paraty.auth.AuthenticationDemo

@Composable
fun MyApp() {
    AuthenticationDemo(
        modifier = Modifier.fillMaxSize()
    )
}
```

### Opción 2: Usar pantallas individuales

```kotlin
import com.ruben.paraty.auth.LoginScreen
import com.ruben.paraty.auth.RegisterScreen

// En tu app con navegación
@Composable
fun AuthFlow(navController: NavController) {
    LoginScreen(
        onNavigateToRegister = { navController.navigate("register") },
        onNavigateToResetPassword = { navController.navigate("reset") },
        onLoginSuccess = { 
            // Navegar a la pantalla principal
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    )
}
```

## 📝 Ejemplo Completo con Navigation

```kotlin
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruben.paraty.auth.*

@Composable
fun ParatyApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToResetPassword = { navController.navigate("reset") },
                onLoginSuccess = { 
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = { 
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("reset") {
            ResetPasswordScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onResetSuccess = { 
                    navController.navigate("login") {
                        popUpTo("reset") { inclusive = true }
                    }
                }
            )
        }
        
        // Tus otras pantallas...
    }
}
```

## 🔧 Personalización

### Cambiar colores

Todos los componentes usan el `ParatyBlue` definido en tu theme. Para cambiar los colores:

1. Edita `/theme/Color.kt`:
```kotlin
val ParatyBlue = Color(0xFF4D71DC) // Cambia este color
```

2. O sobrescribe directamente en AuthComponents.kt

### Agregar validaciones personalizadas

En cada pantalla, puedes modificar la función `performLogin()`, `performRegister()`, o `performReset()`:

```kotlin
fun performLogin() {
    when {
        // Tus validaciones personalizadas
        email.isBlank() -> errorMessage = "Email requerido"
        !isValidEmail(email) -> errorMessage = "Email inválido"
        // ... más validaciones
        else -> {
            // Tu lógica de autenticación
            viewModel.login(email, password) { success ->
                if (success) onLoginSuccess()
                else errorMessage = "Credenciales inválidas"
            }
        }
    }
}
```

## ✨ Características

- ✅ Diseño moderno y limpio inspirado en Doctolib
- ✅ Animaciones sutiles y micro-interacciones
- ✅ Validación de campos en tiempo real
- ✅ Manejo de errores con mensajes claros
- ✅ Toggle de visibilidad de contraseñas
- ✅ Selector de tipo de cuenta (Personal/Negocio)
- ✅ Campos condicionales según tipo de cuenta
- ✅ Estados de carga (loading)
- ✅ Navegación entre pantallas
- ✅ Accesibilidad con íconos descriptivos
- ✅ Responsive y adaptable

## 🎯 Próximos Pasos

1. **Integrar con tu backend**: Conecta las funciones `performLogin()` y `performRegister()` con tu API
2. **Agregar OAuth**: Botones para login con Google, Apple, etc.
3. **Persistencia**: Guardar sesión con DataStore o SharedPreferences
4. **Validación avanzada**: Regex más estrictos, verificación de email, etc.
5. **Animaciones de transición**: Agregar transiciones entre pantallas

## 📸 Screenshots

Ver las imágenes generadas que muestran el diseño final de las pantallas.

---

**Creado con 💙 para Paraty**
