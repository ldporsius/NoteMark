package nl.codingwithlinda.notemark.feature_settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.feature_settings.presentation.state.SettingsAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onAction: (SettingsAction) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBack()
                        }
                    ) {
                        Icon(painter = painterResource(R.drawable.chevron_left), contentDescription = "back")
                    }
                }
            )
        }
    ) {padding ->
        Column(
            modifier = modifier.padding(padding)
                .padding(16.dp)
        ) {
            CompositionLocalProvider (LocalContentColor.provides(nl.codingwithlinda.notemark.design_system.ui.theme.error)) {
                Row(
                   modifier = Modifier
                       .clickable{
                        onAction(SettingsAction.Logout)
                    },
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(R.drawable.log), contentDescription = "logout")
                    Text("Logout")

                }
            }
        }
    }
}