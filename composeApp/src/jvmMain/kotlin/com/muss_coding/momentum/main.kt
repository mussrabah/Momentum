package com.muss_coding.momentum

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Momentum",
        alwaysOnTop = true
    ) {
        App()
    }
}