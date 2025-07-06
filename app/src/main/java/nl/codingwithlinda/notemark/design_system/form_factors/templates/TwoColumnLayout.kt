package nl.codingwithlinda.notemark.design_system.form_factors.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper

@Composable
fun TwoColumnLayout(
    comp1: @Composable () -> Unit,
    comp2: @Composable () -> Unit,
    modifier: Modifier = Modifier) {

    val layoutHorizontal = ScreenSizeHelper.collectScreenInfo().let {
        ScreenSizeHelper.canDisplayTwoPanes(it)
    }

    if (layoutHorizontal){
        Row(modifier = modifier){
            Box(modifier = Modifier.weight(1f)){
                comp1()
            }
            Box(modifier = Modifier.weight(1f)){
                comp2()
            }
        }
    }
    else{
        Column(
            modifier = modifier
        ) {
            comp1()
            comp2()
        }
    }
}