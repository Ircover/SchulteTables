package ru.ircover.schultetables.util

import android.app.Activity
import ru.ircover.schultetables.util.di.PresentationComponentProvider

fun Activity.getApplicationPresentationComponent() = (applicationContext as PresentationComponentProvider).get()