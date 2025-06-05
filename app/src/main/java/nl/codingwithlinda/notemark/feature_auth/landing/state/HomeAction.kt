package nl.codingwithlinda.notemark.feature_auth.landing.state

sealed interface HomeAction {
    data object GetStartedAction: HomeAction
    data object HomeLoginAction: HomeAction
}