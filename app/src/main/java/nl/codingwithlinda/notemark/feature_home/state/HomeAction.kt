package nl.codingwithlinda.notemark.feature_home.state

sealed interface HomeAction {
    data object GetStartedAction: HomeAction
    data object LoginAction: HomeAction
}