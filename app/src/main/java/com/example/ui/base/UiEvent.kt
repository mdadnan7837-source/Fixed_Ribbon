package com.example.ui.base

interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    data class Navigate(val route: String) : UiEvent
    object NavigateBack : UiEvent
}
