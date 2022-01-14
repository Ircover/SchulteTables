package ru.ircover.schultetables.util

import androidx.fragment.app.Fragment
import ru.ircover.schultetables.util.di.PresentationComponentProvider

fun Fragment.getPresentationComponent() = (activity as? PresentationComponentProvider)?.get()