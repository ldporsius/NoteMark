package nl.codingwithlinda.notemark.design_system.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nl.codingwithlinda.notemark.R

val fontFamilySpaceGrotesk = FontFamily(
    fonts = listOf(
        Font(R.font.space_grotesk_medium, weight = FontWeight.Medium),
        Font(R.font.space_grotesk_bold, weight = FontWeight.Bold),)
)

val fontFamilyInter = FontFamily(
    fonts = listOf(
        Font(R.font.inter, weight = FontWeight.Medium),
        Font(R.font.inter, weight = FontWeight.Bold),)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    ,
    bodyMedium = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamilySpaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilySpaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight =36.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamilySpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)