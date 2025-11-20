package com.ruben.paraty.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UIColor

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun SFSymbol(
    symbolName: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    UIKitView(
        factory = {
            val imageView = UIImageView()
            val image = UIImage.systemImageNamed(symbolName)
            imageView.image = image

            // Aplicar tint si está especificado
            if (tint != Color.Unspecified) {
                imageView.tintColor = UIColor(
                    red = tint.red.toDouble(),
                    green = tint.green.toDouble(),
                    blue = tint.blue.toDouble(),
                    alpha = tint.alpha.toDouble()
                )
            }

            imageView
        },
        modifier = modifier.size(20.dp)
    )
}

@Composable
actual fun EmailIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "envelope.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun LockIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "lock.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun PersonIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "person.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun CheckIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "checkmark",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun AccountCircleIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "person.crop.circle.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun ShoppingCartIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "cart.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun LocationIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "mappin.circle.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun VisibilityIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "eye.fill",
        modifier = modifier,
        tint = tint
    )
}

@Composable
actual fun VisibilityOffIcon(
    modifier: Modifier,
    tint: Color
) {
    SFSymbol(
        symbolName = "eye.slash.fill",
        modifier = modifier,
        tint = tint
    )
}
