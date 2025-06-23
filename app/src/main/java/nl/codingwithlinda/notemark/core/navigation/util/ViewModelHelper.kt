package nl.codingwithlinda.notemark.core.navigation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.createViewModel(
    factory: ViewModelProvider.Factory? = null,
    navController: NavController
): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel<T>(
        factory = factory
    )
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel<T>(
        viewModelStoreOwner = parentEntry,
        factory = factory)
}