package com.myapplication

import AndroidStringResolver
import GameViewModel
import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import ui.Game
import ui.Start

class MainActivity : AppCompatActivity() {

    private val gameViewModel by viewModels<GameViewModel> { GameViewModel.Factory }
    private val andtroidStringResolver = AndroidStringResolver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Start(andtroidStringResolver::resolveString) {  }
//            MainView(gameViewModel)
        }
    }
}