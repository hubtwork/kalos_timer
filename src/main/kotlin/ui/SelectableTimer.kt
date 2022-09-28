package ui

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.util.Duration
import style.MainTheme
import tornadofx.*
import tornadofx.Stylesheet.Companion.radioButton
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener


class SelectableTimerController(
): ViewModel() {
    enum class Phase(val time: Long) {
        Phase1(60), Phase2(45), Phase3(30)
    }
    private var timerLimit = Phase.Phase1.time * 1000
    val phaseProperty = SimpleObjectProperty(Phase.Phase1)
    fun selectPhase(state: Phase) {
        timerLimit = state.time * 1000
        start()
    }
    private var timeLine: Timeline? = null
    val timeProperty = SimpleLongProperty(timerLimit)
    var timer by timeProperty
    val borderProperty = SimpleObjectProperty(MainTheme.blackBod)
    var border by borderProperty

    private val timerTick = EventHandler<ActionEvent> {
        timer -= 1
        if (timer == 5000L) border = MainTheme.redBod
        if (timer == 0L) {
            timeProperty.set(timerLimit)
            border = MainTheme.blackBod
        }
    }
    fun start() {
        timeProperty.set(timerLimit)
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
        start()
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
                label(title)
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
                        action { model.start() }
                        style {
                            prefWidth = 60.px
                        }
                    }
                    button(text = "리셋") {
                        action { model.reset() }
                        style {
                            prefWidth = 60.px
                        }
                    }
                }
                togglegroup {
                    radiobutton("2-1 ( 60 )", value = SelectableTimerController.Phase.Phase1)
                    radiobutton("2-2 ( 45 )", value = SelectableTimerController.Phase.Phase2)
                    radiobutton("2-3 ( 30 )", value = SelectableTimerController.Phase.Phase3)
                    selectedValueProperty<SelectableTimerController.Phase>().bindBidirectional(model.phaseProperty)
                }
            }

            style {
                padding = box(20.px)
            }
        }
}