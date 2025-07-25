package nl.codingwithlinda.notemark.design_system.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val LocalButtonShape = compositionLocalOf {
    RoundedCornerShape(12.dp)
}
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(24.dp) ,
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(0.dp)
)

