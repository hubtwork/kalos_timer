package com.ui

import com.style.MainTheme
import javafx.animation.Animation.INDEFINITE
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.util.Duration
import tornadofx.*

class TestController(
    private val initialTime: Long
): ViewModel() {
    private var timeLine: Timeline? = null
    private val _timeLimit = SimpleLongProperty(5000L)
    private var timeLimit by _timeLimit
    val timeProperty = SimpleLongProperty(initialTime)
    var timer by timeProperty
    val borderProperty = SimpleObjectProperty(MainTheme.blackBod)
    private var border by borderProperty
    val isOnTick = SimpleBooleanProperty(false)
    var onTick by isOnTick
    init {
        subscribe<TimeLimitChangeEvent> {
            event -> _timeLimit.set(event.time)
            if (timer < timeLimit) border = MainTheme.redBod
        }
    }

    private val timerTick = EventHandler<ActionEvent> {
        timer -= 1
        isOnTick.set(true)
        if (timer == timeLimit) border = MainTheme.redBod
        if (timer == 0L) {
            timeProperty.set(initialTime)
            border = MainTheme.blackBod
        }
    }
    fun onClickTickButton() {
        if (onTick) {
            onTick = false
            timeLine?.stop()
        } else {
            onTick = true
            start()
        }
    }
    private fun start(isReset: Boolean = false) {
        if (isReset) timeProperty.set(initialTime)
        timeLine = Timeline(
            KeyFrame(
                Duration.millis(1.0),
                timerTick
            )
        ).apply {
            cycleCount = INDEFINITE
            play()
        }
    }
    fun reset() {
        timeLine?.stop()
        border = MainTheme.blackBod
        start(true)
    }

}
class BasicTimerView(
    title: String,
    limitSecond: Long = 5
): View() {
    private val model = TestController(initialTime = limitSecond * 1000)

    override val root =
        hbox {
            vbox(alignment = Pos.CENTER) {
                label(title) {
                    style {
                        prefHeight = 30.px
                        minHeight = 30.px
                    }
                }
                vbox(alignment = Pos.CENTER) {
                    text{
                        font = Font(20.0)
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    val time = model.timer
                                    String.format(
                                        "%02d:%02d",
                                        time / 1000,
                                        time % 1000 / 10,
                                    )
                                }, model.timeProperty
                            )
                        )
                        style {
                            alignment = Pos.BASELINE_CENTER
                        }
                    }

                    addClass(MainTheme.timerContainer)
                    bindClass(model.borderProperty)
                }
                hbox {
                    button(text = "시작") {
                        action { model.onClickTickButton() }
                        style {
                            prefWidth = 60.px
                        }
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    if (model.onTick) "정지"
                                    else "시작"
                                }, model.isOnTick
                            )
                        )
                    }
                    button(text = "리셋") {
                        action { model.reset() }
                        style {
                            prefWidth = 60.px
                        }
                    }
                }
            }

            style {
                padding = box(20.px)
            }
        }
}