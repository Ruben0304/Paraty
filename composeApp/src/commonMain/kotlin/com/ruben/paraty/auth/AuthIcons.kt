package com.ruben.paraty.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Iconos multiplataforma para autenticación
 * En Android usa Material Icons, en iOS usa SF Symbols
 */

@Composable
expect fun EmailIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun LockIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun PersonIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun CheckIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun AccountCircleIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun ShoppingCartIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun LocationIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun VisibilityIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)

@Composable
expect fun VisibilityOffIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
)
