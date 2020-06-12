package com.owl93.pieviewdemo.common

import android.widget.SeekBar

class SeekbarListener(val endAction: (progress: Int) -> Unit) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) { endAction.invoke(p1) }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}