package com.timer.normal

import com.timer.base.TimerType
import com.style.MainTheme
import com.timer.SoundPack
import com.timer.base.TimerViewModel
import javafx.beans.property.SimpleObjectProperty
import tornadofx.getValue
import tornadofx.setValue

class NormalTimerViewModel (
    param: TimerType.Normal
): TimerViewModel(type = param) {
    // UI Properties
    val _uiTimerBorder = SimpleObjectProperty(MainTheme.blackBod)
    private var uiTimerBorder by _uiTimerBorder

    override fun onUiWarning() {
        SoundPack.getInstance().serveSound()
        uiTimerBorder = MainTheme.redBod
    }
    override fun onUiNormal() {
        uiTimerBorder = MainTheme.blackBod
    }
}