package com.components.normal

import com.components.serveTimeline
import com.model.TimerParam
import com.style.MainTheme
import com.ui.TimeLimitChangeEvent
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

class NormalTimerModel (
    param: TimerParam
): ViewModel() {
    // Consume TimerParam
    private var setTime: Long
    // Properties
    private var timeline: Timeline
    val timeProps = SimpleLongProperty()       // timer property
    private var warningTime: Long     // timer when starts warning
    val isOnTicking = SimpleBooleanProperty(false)    // flag indicates is timer ticking

    // Properties - Getter/Setter
    private var _timeProps by timeProps
    private var _isOnTicking by isOnTicking

    init {
        // Set eventBus subscriber
        subscribe<TimeLimitChangeEvent> { event ->
            val newWarningTime = event.time
            warningTime = newWarningTime
            if (_timeProps <= newWarningTime) onUiWarning()
        }
        // init Properties
        warningTime = param.warningTime
        setTime = param.timeMillis
        _timeProps = param.timeMillis
        // init Timeline
        timeline = serveTimeline {
            _timeProps -= 1
            if (_timeProps == warningTime) onUiWarning()
            if (_timeProps == 0L) {
                _timeProps = setTime
                onUiStart()
            }
        }
    }
    // UI Properties
    val _uiTimerBorder = SimpleObjectProperty(MainTheme.blackBod)
    private var uiTimerBorder by _uiTimerBorder

    private fun onUiStart() {
        uiTimerBorder = MainTheme.blackBod
    }
    private fun onUiWarning() {
        uiTimerBorder = MainTheme.redBod
    }

    fun onClickTimerButton(type: NormalTimerButtonType) {
        when(type) {
            NormalTimerButtonType.StartStop -> {
                if (_isOnTicking) {
                    // timer is on tick
                    _isOnTicking = false
                    timeline.stop()
                } else {
                    // timer is stopped
                    _isOnTicking = true
                    timeline.play()
                }
            }
            NormalTimerButtonType.Reset -> {
                _timeProps = setTime
                _isOnTicking = true
                timeline.play()
            }
        }
    }


}