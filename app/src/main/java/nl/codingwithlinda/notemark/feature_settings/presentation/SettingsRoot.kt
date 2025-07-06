package nl.codingwithlinda.notemark.feature_settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier) {


    SettingsScreen(
        modifier = modifier,
        onAction = viewModel::handleAction
        ,
        onBack = {
            onBack()
        }
    )
}