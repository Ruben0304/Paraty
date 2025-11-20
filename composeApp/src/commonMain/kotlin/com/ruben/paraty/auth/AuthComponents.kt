package com.ruben.paraty.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruben.paraty.theme.ParatyBlue

/**
 * Logo principal de Paraty para pantallas de autenticación
 */
@Composable
fun ParatyLogo(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono de marca estilizado (puedes reemplazar con tu logo)
        LocationIcon(
            modifier = Modifier.size(32.dp),
            tint = ParatyBlue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Paraty",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF1F1F1F)
            )
        )
    }
}

/**
 * Ícono decorativo animado para pantallas de autenticación
 */
@Composable
fun AuthIcon(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = modifier
            .size(100.dp)
            .scale(scale)
            .background(
                color = ParatyBlue.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

/**
 * Campo de texto personalizado con estilo Doctolib
 */
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val isFocused = remember { mutableStateOf(false) }

    val borderColor = if (isFocused.value) ParatyBlue else Color(0xFFE0E0E0)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(28.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono inicial opcional
            leadingIcon?.let { icon ->
                icon()
                Spacer(modifier = Modifier.width(12.dp))
            }
            
            // Campo de texto
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically),
                enabled = enabled,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF1F1F1F)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                keyboardActions = keyboardActions,
                singleLine = true,
                visualTransformation = if (isPassword && !passwordVisible) 
                    PasswordVisualTransformation() 
                else 
                    VisualTransformation.None,
                cursorBrush = SolidColor(ParatyBlue),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color(0xFF9E9E9E)
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )
            
            // Botón de mostrar/ocultar contraseña
            if (isPassword) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(modifier = Modifier.size(20.dp)) {
                        if (passwordVisible) {
                            VisibilityIcon(
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF9E9E9E)
                            )
                        } else {
                            VisibilityOffIcon(
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF9E9E9E)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Botón principal con estilo Doctolib
 */
@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ParatyBlue,
            contentColor = Color.White,
            disabledContainerColor = ParatyBlue.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

/**
 * Switch personalizado para seleccionar tipo de cuenta
 */
@Composable
fun AccountTypeSelector(
    isBusinessAccount: Boolean,
    onAccountTypeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Tipo de cuenta",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5F6368)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Opción Personal
            AccountTypeOption(
                text = "Personal",
                icon = { PersonIcon(modifier = Modifier.size(24.dp)) },
                isSelected = !isBusinessAccount,
                onClick = { onAccountTypeChange(false) },
                modifier = Modifier.weight(1f)
            )

            // Opción Negocio
            AccountTypeOption(
                text = "Negocio",
                icon = { ShoppingCartIcon(modifier = Modifier.size(24.dp)) },
                isSelected = isBusinessAccount,
                onClick = { onAccountTypeChange(true) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Opción individual para el selector de tipo de cuenta
 */
@Composable
private fun AccountTypeOption(
    text: String,
    icon: @Composable () -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) ParatyBlue.copy(alpha = 0.1f) else Color.White
    val borderColor = if (isSelected) ParatyBlue else Color(0xFFE0E0E0)
    val textColor = if (isSelected) ParatyBlue else Color(0xFF5F6368)

    Box(
        modifier = modifier
            .height(64.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = textColor
                )
            )
        }
    }
}

/**
 * Texto con enlace clicable
 */
@Composable
fun TextWithLink(
    normalText: String,
    linkText: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = normalText,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF5F6368)
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = linkText,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ParatyBlue
            ),
            modifier = Modifier.clickable(
                onClick = onLinkClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
        )
    }
}
