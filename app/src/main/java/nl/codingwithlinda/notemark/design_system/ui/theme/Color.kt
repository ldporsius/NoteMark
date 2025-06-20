package nl.codingwithlinda.notemark.design_system.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


//scheme
val surface = Color(0xFFEFEFF2)
val surfaceLowest = Color(0xFFffffff)
val onSurface = Color(0xFF1b1b1b)
val onSurfaceLight = onSurface.copy(.5f)
val onSurfaceVariant = Color(0xFF535364)
val error = Color(0xFFE1294B)


//brand
val primary = Color(0xFF5977F7)
val onPrimary = Color(0xFFffffff)

val backgroundGradient = Brush.verticalGradient(
    colors = listOf(primary, primary.copy(.1f)),
    startY = 0f,
    endY = 1000f
)

val fabGradient = Brush.verticalGradient(
    colors = listOf(primary.copy(.5f), primary),
    startY = 0f,
    endY = Float.POSITIVE_INFINITY
)

val backgroundGradientGrey = Brush.verticalGradient(
    colors = listOf(surface.copy(.5f), surface),
    startY = 0f,
    endY = 1000f
)


val background_landing = Color(0xFFE0EAFF)