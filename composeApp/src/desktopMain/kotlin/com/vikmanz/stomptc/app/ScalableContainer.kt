package com.vikmanz.stomptc.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun ScalableContainer(
    scale: Float,
    content: @Composable () -> Unit
) {

    val defaultDensity = LocalDensity.current
    val scaledDensity = Density(defaultDensity.density * scale, defaultDensity.fontScale * scale)

    CompositionLocalProvider(
            value = LocalDensity provides scaledDensity,
            content = content
    )

}