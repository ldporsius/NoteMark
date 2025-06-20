package nl.codingwithlinda.notemark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager

class MainViewModel(
    private val sessionManager: SessionManager
): ViewModel() {

    var isLoading = true

    init {
        viewModelScope.launch {
            sessionManager.isSessionValid()
            isLoading = false
        }
    }
}