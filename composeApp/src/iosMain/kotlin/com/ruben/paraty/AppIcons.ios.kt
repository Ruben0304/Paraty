package com.ruben.paraty

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import platform.UIKit.UIImage
import platform.UIKit.UIImageSymbolConfiguration
import platform.UIKit.UIImageView
import kotlinx.cinterop.ExperimentalForeignApi

// Para iOS usamos componentes composables personalizados en lugar de ImageVector
// porque SF Symbols no son compatibles directamente con ImageVector

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SFSymbolIcon(
    symbolName: String,
    contentDescription: String?,
    modifier: Modifier
) {
    UIKitView(
        factory = {
            val imageView = UIImageView()
            val image = UIImage.systemImageNamed(symbolName)
            imageView.image = image
            imageView.tintColor = platform.UIKit.UIColor.whiteColor
            imageView
        },
        modifier = modifier.size(24.dp)
    )
}

