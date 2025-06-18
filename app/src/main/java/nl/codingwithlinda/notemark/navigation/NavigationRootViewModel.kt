package nl.codingwithlinda.notemark.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationRootViewModel: ViewModel() {

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn = _isLoggedIn.asStateFlow()


}