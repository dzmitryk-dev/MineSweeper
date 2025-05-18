package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fakes.FakeStringResolver
import ui.MainView

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainView(FakeStringResolver::resolveString)
    }
}