package nl.codingwithlinda.notemark.design_system.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext



private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = primary,
    background = surface,
    surface = surface,
    surfaceContainerLowest = surfaceLowest,
    onPrimary = onPrimary,
    onSecondary = onPrimary,
    onTertiary = onPrimary,
    onBackground = onSurface,
    onSurface = onSurface,

)

@Composable
fun customTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = surface,
        focusedContainerColor = surfaceLowest,
        unfocusedBorderColor = surface,
        focusedBorderColor = onSurfaceVariant,
        errorContainerColor = surfaceLowest,
        errorBorderColor = error,
        errorPlaceholderColor = error,
        unfocusedSupportingTextColor = onSurface,
        focusedSupportingTextColor = onSurface,
        unfocusedPlaceholderColor = onSurface,
        focusedPlaceholderColor = onSurfaceLight
    )
}

@Composable
fun customCardColors(): CardColors {
    return CardColors(
        containerColor = surfaceLowest,
        contentColor = onSurface,
        disabledContainerColor = surface,
        disabledContentColor = onSurface
    )
}


@Composable
fun NoteMarkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}