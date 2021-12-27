package ru.ircover.schultetables.view.activity

import android.os.Bundle
import moxy.MvpAppCompatActivity
import ru.ircover.schultetables.R

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}