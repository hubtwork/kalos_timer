package com.timer.normal

import com.timer.model.TimerType
import com.style.MainTheme
import com.timer.base.TimerViewModel
import com.util.resource.Resources
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
        uiTimerBorder = MainTheme.redBod
    }
    override fun onUiNormal() {
        uiTimerBorder = MainTheme.blackBod
    }
}