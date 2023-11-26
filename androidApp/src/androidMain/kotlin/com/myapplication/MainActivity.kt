package com.myapplication

import AndroidStringResolver
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ui.MainView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val androidStringResolver = AndroidStringResolver(this)

        setContent {
            MainView(androidStringResolver::resolveString)
        }
    }
}