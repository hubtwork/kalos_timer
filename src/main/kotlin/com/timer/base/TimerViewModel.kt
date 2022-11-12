package com.timer.base

import com.timer.model.TimerActionType
import com.timer.model.TimerType
import com.timer.serveTimeline
import com.ui.TimeLimitChangeEvent
import com.util.resource.sound.OnSoundPlayListener
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
abstract class TimerViewModel(
    type: TimerType
): ViewModel() {
    val title: String
    protected var setTime: Long

    private val timeline: Timeline
    // Properties
    val timeProps = SimpleLongProperty()
    val onPlayingProps = SimpleBooleanProperty()
    private val warningTimeProps = AtomicLong()
    // Properties - Getter/Setter
    private var timeValue by timeProps
    private var onPlayingValue by onPlayingProps
    // Listeners
    private var mListener: OnSoundPlayListener? = null

    init {
        // Set eventBus subscriber
        subscribe<TimeLimitChangeEvent> { event ->
            val newWarningTime = event.time
            warningTimeProps.set(newWarningTime)
            if (timeValue <= newWarningTime) onUiWarning()
        }
        // init Properties
        title = type.title
        val initialTime = when(type) {
            is TimerType.Normal -> type.initialTime
            is TimerType.Selectable -> type.initialPhase.time
        }.timeMillis
        setTime = initialTime
        timeValue = initialTime
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
    fun registerOnSoundPlayListener(_listener: OnSoundPlayListener) {
        if (mListener != null) mListener = null
        mListener = _listener
    }

    protected open fun onUiWarning() {
        mListener?.playSound()
    }
    protected abstract fun onUiNormal()
    // for selector timer
    fun onClickTimerButton(type: TimerActionType.Basic) {
        when(type) {
            TimerActionType.Basic.StartStop -> {
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
            TimerActionType.Basic.Reset -> {
                timeValue = setTime
                onPlayingValue = true
                timeline.play()
            }
        }
    }

}