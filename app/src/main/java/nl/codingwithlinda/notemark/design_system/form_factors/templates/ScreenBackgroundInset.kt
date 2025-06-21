package nl.codingwithlinda.notemark.design_system.form_factors.templates

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenType
import nl.codingwithlinda.notemark.design_system.ui.theme.background_landing
import nl.codingwithlinda.notemark.design_system.ui.theme.onSurface

/*
A 'template' to show a background image and an inset with content.
The inset appears at the bottom if orientation is portrait.
The inset appears at the right if orientation is landscape.
 */

@Composable
fun ScreenBackgroundInset(
    @DrawableRes backgroundImageRes: Int,
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
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceOrientation = LocalConfiguration.current.orientation

    val screenInfo = ScreenSizeHelper.getScreenInfo(windowSizeClass, deviceOrientation)
    println("SCREEN INFO: $screenInfo")

    val shouldDisplayVertical = ScreenSizeHelper.canDisplayVertical(screenInfo)

    Surface(
        color = background_landing,
        contentColor = onSurface,
        modifier = modifier
    ) {
        val insetShape = when(shouldDisplayVertical){
            true -> RoundedCornerShape(topStartPercent = 15, topEndPercent = 15)
            false -> RoundedCornerShape(topStartPercent = 15, bottomStartPercent = 15)
        }

        BoxWithConstraints (modifier = Modifier) {
            val desiredInsetWidth =  when(shouldDisplayVertical){
                true -> {
                    when(screenInfo.width_height.width){
                        ScreenType.EXPANDED -> maxWidth/2
                        ScreenType.MEDIUM -> maxWidth * .9f
                        else -> maxWidth
                    }
                }
                false -> {
                    when(screenInfo.width_height.width){
                        ScreenType.EXPANDED -> maxWidth/2
                        ScreenType.MEDIUM -> maxWidth/2
                        else -> maxWidth
                    }
                }
            }

            if (shouldDisplayVertical) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
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

                    )
                }
            }
            else  {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    createImage(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    )
                    Box(modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                    ) {
                        createInset(
                            modifier = Modifier
                                .background(color = Color.White, shape = insetShape)
                                .width(desiredInsetWidth)

                                .padding(vertical = 48.dp, horizontal = 24.dp)

                        )
                    }
                }
            }
        }
    }
}