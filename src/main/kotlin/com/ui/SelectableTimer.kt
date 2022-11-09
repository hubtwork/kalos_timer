package com.ui

import com.style.MainTheme
import javafx.animation.Animation
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


class SelectableTimerController(
): ViewModel() {
    enum class Phase(val time: Long) {
        Phase1(60), Phase2(45), Phase3(30)
    }
    private var timerLimit = Phase.Phase1.time * 1000
    val phaseProperty = SimpleObjectProperty(Phase.Phase1)
    private val _timeLimit = SimpleLongProperty(5000L)
    private var timeLimit by _timeLimit
    val isOnTick = SimpleBooleanProperty(false)
    var onTick by isOnTick
    init {
        subscribe<TimeLimitChangeEvent> {
                event -> _timeLimit.set(event.time)
            if (timer < timeLimit) border = MainTheme.redBod
        }
    }
    fun selectPhase(state: Phase) {
        timerLimit = state.time * 1000
        reset()
    }
    private var timeLine: Timeline? = null
    val timeProperty = SimpleLongProperty(timerLimit)
    var timer by timeProperty
    val borderProperty = SimpleObjectProperty(MainTheme.blackBod)
    var border by borderProperty

    private val timerTick = EventHandler<ActionEvent> {
        timer -= 1
        isOnTick.set(true)
        if (timer == timeLimit) border = MainTheme.redBod
        if (timer == 0L) {
            timeProperty.set(timerLimit)
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
    fun start(isReset: Boolean = false) {
        if (isReset) timeProperty.set(timerLimit)
        timeLine = Timeline(
            KeyFrame(
                Duration.millis(1.0),
                timerTick
            )
        ).apply {
            cycleCount = Animation.INDEFINITE
            play()
        }
    }

    fun reset() {
        timeLine?.stop()
        border = MainTheme.blackBod
        start(true)
    }

}
class SelectableTimerView(
    title: String
): View() {
    private val model = SelectableTimerController()

    init {
        model.phaseProperty.addListener { observable, oldValue, newValue ->
            if (newValue != null) model.selectPhase(newValue)
        }
    }

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
                    text {
                        font = Font(20.0)
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    val time = model.timeProperty.get()
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
                togglegroup {
                    radiobutton("2-1 ( 60 )", value = SelectableTimerController.Phase.Phase1) {
                        style {
                            paddingTop = 10
                        }
                    }
                    radiobutton("2-2 ( 45 )", value = SelectableTimerController.Phase.Phase2) {
                        style {
                            paddingTop = 5
                        }
                    }
                    radiobutton("2-3 ( 30 )", value = SelectableTimerController.Phase.Phase3) {
                        style {
                            paddingTop = 5
                        }
                    }
                    selectedValueProperty<SelectableTimerController.Phase>().bindBidirectional(model.phaseProperty)
                }
            }

            style {
                padding = box(20.px)
            }
        }
}