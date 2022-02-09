package ru.ircover.schultetables.main.view

import android.os.Bundle
import moxy.MvpAppCompatActivity
import ru.ircover.schultetables.R
import ru.ircover.schultetables.util.di.PresentationComponent
import ru.ircover.schultetables.util.di.PresentationComponentProvider
import ru.ircover.schultetables.util.getApplicationPresentationComponent

class MainActivity : MvpAppCompatActivity(), PresentationComponentProvider {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun get(): PresentationComponent = getApplicationPresentationComponent()
}