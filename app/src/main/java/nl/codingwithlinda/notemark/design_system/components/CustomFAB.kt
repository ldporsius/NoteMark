package nl.codingwithlinda.notemark.design_system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.ui.theme.fabGradient

@Composable
fun CustomFAB(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface (
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.small,
    ){
        Box(
            modifier = Modifier
                .size(
                    width = 64.dp,
                    height = 64.dp
                )
                .clickable(onClick = onClick)
                .background(fabGradient, shape = MaterialTheme.shapes.small),

            contentAlignment = androidx.compose.ui.Alignment.Center

        ) {
            content()
        }
    }
}