package nl.codingwithlinda.notemark.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.design_system.ui.theme.backgroundGradient
import nl.codingwithlinda.notemark.design_system.ui.theme.onPrimary
import nl.codingwithlinda.notemark.design_system.ui.theme.primary

@Composable
fun HomeRoot() {
    Scaffold(
        containerColor = primary,
        contentColor = onPrimary
    ) {innerPadding ->
        Box(Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
            .padding(innerPadding)
            ,
            contentAlignment = Alignment.Center){
            Text(text = "This is the home screen")
        }
    }

}