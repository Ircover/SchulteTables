package ru.ircover.schultetables.util

import android.widget.SeekBar

fun SeekBar.setOnSeekBarChangeListener(listener: (Int) -> Unit) {
    setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
            if(fromUser) {
                listener(progress)
            }
        }
        override fun onStartTrackingTouch(p0: SeekBar?) { }
        override fun onStopTrackingTouch(p0: SeekBar?) { }
    })
}