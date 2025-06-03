package nl.codingwithlinda.notemark.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenTwoComposables
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginForm
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginHeader
import nl.codingwithlinda.notemark.feature_home.components.HomeScreenInset

@Composable
fun HomeScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {

        ScreenTwoComposables(
            comp1 = {
                Image(
                    painter = painterResource(id = R.drawable.landing_page),
                    contentDescription = "Landing page background",
                    modifier = Modifier,
                    contentScale = ContentScale.FillBounds
                )
            },
            comp2 = {
               HomeScreenInset(
                   modifier = Modifier.fillMaxWidth()
                       .background(color = Color.Companion.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                       .padding(16.dp)
               )
            },
            modifier = Modifier.fillMaxSize()
                .background(color = primary.copy(.12f))
        )
    }


}