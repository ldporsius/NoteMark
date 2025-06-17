package nl.codingwithlinda.notemark.design_system.form_factors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LimitedHeightLayout(
    comp1: @Composable () -> Unit,
    comp2: @Composable () -> Unit,
    modifier: Modifier = Modifier) {

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)){
        comp1()
        comp2()
    }

}