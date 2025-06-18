package nl.codingwithlinda.notemark.design_system.form_factors

import android.content.res.Configuration
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

data class ScreenInfo(
    val orientation: Orientation,
    val width_height: ScreenWidthHeight
)
data class ScreenWidthHeight(
    val width: ScreenType,
    val height: ScreenType
)
enum class Orientation{
    PORTRAIT,
    LANDSCAPE
}
enum class ScreenType {
    COMPACT,
    MEDIUM,
    EXPANDED,
    UNKNOWN
}

val mapHeightSizeClassToScreenType = mapOf(
    WindowHeightSizeClass.COMPACT to ScreenType.COMPACT,
    WindowHeightSizeClass.MEDIUM to ScreenType.MEDIUM,
    WindowHeightSizeClass.EXPANDED to ScreenType.EXPANDED
)
val mapWidthSizeClassToScreenType = mapOf(
    WindowWidthSizeClass.COMPACT to ScreenType.COMPACT,
    WindowWidthSizeClass.MEDIUM to ScreenType.MEDIUM,
    WindowWidthSizeClass.EXPANDED to ScreenType.EXPANDED
)
object ScreenSizeHelper {

    @Composable
    fun collectScreenInfo(): ScreenInfo {
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceOrientation = LocalConfiguration.current.orientation
        return getScreenInfo(windowSizeClass, deviceOrientation)
    }
    private fun getScreenWidthHeight(
        windowSizeClass: WindowSizeClass,
    ): ScreenWidthHeight {
        val heightScreenType = mapHeightSizeClassToScreenType[windowSizeClass.windowHeightSizeClass] ?: ScreenType.UNKNOWN
        val widthScreenType = mapWidthSizeClassToScreenType[windowSizeClass.windowWidthSizeClass] ?: ScreenType.UNKNOWN
        return ScreenWidthHeight(widthScreenType, heightScreenType)
    }

    private fun getOrientation(deviceOrientation: Int): Orientation {
        return if (deviceOrientation == Configuration.ORIENTATION_PORTRAIT) {
            Orientation.PORTRAIT
        } else {
            Orientation.LANDSCAPE
        }
    }

    fun getScreenInfo( windowSizeClass: WindowSizeClass,
                       deviceOrientation: Int): ScreenInfo{
        val widthHeight = getScreenWidthHeight(windowSizeClass)
        val orientation = getOrientation(deviceOrientation)
        return ScreenInfo(orientation, widthHeight)
    }
    fun canDisplayVertical(screenInfo: ScreenInfo): Boolean {
        return screenInfo.orientation == Orientation.PORTRAIT
    }
    fun canDisplayTwoPanes(screenInfo: ScreenInfo): Boolean {
        return screenInfo.orientation == Orientation.LANDSCAPE && screenInfo.width_height.width == ScreenType.EXPANDED
    }
    fun isLimitedVertical(screenInfo: ScreenInfo): Boolean {
        return screenInfo.width_height.height == ScreenType.COMPACT
    }


}