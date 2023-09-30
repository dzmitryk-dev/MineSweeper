package com.myapplication

import GameViewModel
import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import ui.Game

class MainActivity : AppCompatActivity() {

    private val gameViewModel by viewModels<GameViewModel> { GameViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainView(gameViewModel)
        }
    }
}