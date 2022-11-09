package com.components.normal

import com.components.serveTimeline
import com.timer.TimerType
import com.style.MainTheme
import com.timer.TimerViewModel
import com.ui.TimeLimitChangeEvent
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel
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