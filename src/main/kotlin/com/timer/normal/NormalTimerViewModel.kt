package com.timer.normal

import com.timer.model.TimerType
import com.style.MainTheme
import com.timer.base.TimerViewModel
import javafx.beans.property.SimpleObjectProperty
import tornadofx.getValue
import tornadofx.setValue

class NormalTimerViewModel (
    type: TimerType.Normal
): TimerViewModel(type = type) {
    // UI Properties
    val timerBorderProps = SimpleObjectProperty(MainTheme.blackBod)
    private var uiTimerBorder by timerBorderProps

    override fun onUiWarning() {
        super.onUiWarning()
        uiTimerBorder = MainTheme.redBod
    }
    override fun onUiNormal() {
        uiTimerBorder = MainTheme.blackBod
    }
}