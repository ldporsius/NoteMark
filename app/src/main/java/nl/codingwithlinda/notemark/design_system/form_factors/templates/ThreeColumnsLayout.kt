package nl.codingwithlinda.notemark.design_system.form_factors.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ThreeColumnsLayout(
    comp1: @Composable () -> Unit,
    comp2: @Composable () -> Unit,
    comp3: @Composable () -> Unit,
    modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxSize()
        ,){
        Box(modifier = Modifier){
            comp1()
        }
        Box(modifier = Modifier.weight(1f)){
            comp2()
        }
        Box(modifier = Modifier){
            comp3()
        }

    }
}