package ui

import javafx.animation.Animation.INDEFINITE
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableLongValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.text.Font
import javafx.util.Duration
import tornadofx.*
import java.util.Timer
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.timerTask

class TestController: ViewModel() {
    private val isTimerOnGoing = AtomicBoolean(false)
    private var timeLine: Timeline? = null
    var timer: Long = 10000
    val timeProperty = SimpleLongProperty(5000)
    val timiemi = SimpleStringProperty("가")
    var timmm: String by timiemi

    private val timerTick = EventHandler<ActionEvent> {
        timeProperty.value -= 1
        if (timeProperty.value == 0L) {
            timeProperty.set(5000)
        }
    }
    fun start() {
        timeProperty.set(5000)
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
    fun stop() { timeLine?.stop() }

    fun reset() {
        timeLine?.stop()
        start()
    }

}
class MainView: View() {
    private val model = TestController()


    override val root =
        hbox {
            vbox {
                label("폭탄타임")
                vbox {
                    text(
                    ) {
                        font = Font(20.0)
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    val time = model.timeProperty.get()
                                    String.format(
                                        "%02d:%02d",
                                        time/1000,
                                        time%1000/10,
                                    )
                                }, model.timeProperty
                            )
                        )

                    }
                    button(text = "시작") {
                        action { model.start() }
                    }
                    button(text = "리셋") {
                        action { model.reset() }
                    }


                    style {
                        prefWidth = 150.px
                        prefHeight = 150.px
                    }
                }
            }

            style {
                padding = box(20.px)
            }
        }

    init {
//        model.start()

    }

}