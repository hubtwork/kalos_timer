package com.timer.base

import com.timer.model.TimerButtonType
import com.timer.model.TimerType
import com.timer.serveTimeline
import com.ui.TimeLimitChangeEvent
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue
import java.util.concurrent.atomic.AtomicLong

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */
abstract class TimerViewModel(type: TimerType): ViewModel() {
    val title: String
    protected var setTime: Long

    protected val timeline: Timeline
    // Properties
    val timeProps = SimpleLongProperty()
    val onPlayingProps = SimpleBooleanProperty()
    private val warningTimeProps = AtomicLong()
    // Properties - Getter/Setter
    private var timeValue by timeProps
    private var onPlayingValue by onPlayingProps


    init {
        // Set eventBus subscriber
        subscribe<TimeLimitChangeEvent> { event ->
            val newWarningTime = event.time
            warningTimeProps.set(newWarningTime)
            if (timeValue <= newWarningTime) onUiWarning()
        }
        // init Properties
        title = type.title
        setTime = type.initialTime.timeMillis
        timeValue = type.initialTime.timeMillis
        warningTimeProps.set(type.warningTime.timeMillis)
        // init Timeline
        timeline = serveTimeline {
            timeValue -= 1
            if (timeValue == warningTimeProps.get()) onUiWarning()
            if (timeValue == 0L) {
                timeValue = setTime
                onUiNormal()
            }
        }
    }
    protected abstract fun onUiWarning()
    protected abstract fun onUiNormal()
    // for selector timer
    fun onClickTimerButton(type: TimerButtonType.Basic) {
        when(type) {
            TimerButtonType.Basic.StartStop -> {
                if (onPlayingValue) {
                    // timer is on playing
                    onPlayingValue = false
                    timeline.stop()
                } else {
                    // timer is stopped
                    onPlayingValue = true
                    timeline.play()
                }
            }
            TimerButtonType.Basic.Reset -> {
                timeValue = setTime
                onPlayingValue = true
                timeline.play()
            }
        }
    }


}