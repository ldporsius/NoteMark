package nl.codingwithlinda.notemark.design_system.form_factors

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.EXPANDED
import androidx.window.core.layout.WindowWidthSizeClass.Companion.MEDIUM
import nl.codingwithlinda.notemark.design_system.ui.theme.background_landing
import nl.codingwithlinda.notemark.design_system.ui.theme.onSurface

/*
A 'template' to show a background image and an inset with content.
The inset appears at the bottom if screen width is compact.
The inset appears at the right if screen width is extended.
 */

@Composable
fun ScreenBackgroundInset(
    @DrawableRes backgroundImageRes: Int,
    windowSize: WindowWidthSizeClass,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {

    @Composable
    fun createImage(modifier: Modifier){
        Image(
            painter = painterResource(id = backgroundImageRes),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
        )
    }

    @Composable
    fun createInset(
        modifier: Modifier,
    ){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) { content() }
    }
    Surface(
        color = background_landing,
        contentColor = onSurface,
        modifier = modifier
    ) {
        val isExpanded = windowSize == EXPANDED
        val alignment = when (windowSize) {
            EXPANDED-> Alignment.CenterEnd
            else -> Alignment.BottomCenter
        }
        val insetShape = when(windowSize){
            EXPANDED -> RoundedCornerShape(topStartPercent = 15, bottomStartPercent = 15)
            else -> RoundedCornerShape(topStartPercent = 15, topEndPercent = 15)
        }
        var insetHeight by remember { mutableStateOf(0.dp) }

        BoxWithConstraints (modifier = Modifier) {
            val density = LocalDensity.current.density

            val desiredInsetWidth =  when(windowSize){
                EXPANDED -> maxWidth/2
                MEDIUM -> maxWidth * .9f
                else -> maxWidth
            }

            when(windowSize){
                EXPANDED -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        createImage(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                        )
                        createInset(
                            modifier =  Modifier
                                .background(color = Color.White, shape = insetShape)
                                .width(desiredInsetWidth)
                                .padding(vertical = 48.dp, horizontal = 24.dp)
                                .onSizeChanged {
                                    insetHeight = it.height.dp
                                    println("SCREEN BACKGROUND INSET: insetHeight: $insetHeight")
                                }
                        )
                    }
                }
                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        createImage(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                        createInset(
                            modifier =  Modifier
                                .background(color = Color.White, shape = insetShape)
                                .width(desiredInsetWidth)
                                .padding(vertical = 48.dp, horizontal = 24.dp)
                                .onSizeChanged {
                                    insetHeight = it.height.dp
                                    println("SCREEN BACKGROUND INSET: insetHeight: $insetHeight")
                                }
                        )
                    }
                }
            }

        }

    }
}