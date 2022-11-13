package com.ui

import com.timer.model.Time
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*

data class TimeLimitChangeEvent(val time: Long): FXEvent()

class TimeLimitControlViewModel(
    initialWarningTime: Time
): ViewModel() {

    val inputEnableState = SimpleBooleanProperty(false)
    fun changeEnableState() {
        if (timer.isNullOrEmpty()) return
        val current = inputEnableState.get()
        if (current) fire(TimeLimitChangeEvent(timer.toLong() * 1000))
        inputEnableState.set(!current)
    }
    val buttonEnableState = SimpleBooleanProperty(true)
    fun changeButtonEnableState(state: Boolean) {
        buttonEnableState.set(state)
    }
    val timerInput = SimpleStringProperty(initialWarningTime.seconds.toString())
    private var timer by timerInput
}

class TimeLimitControlView(
    private val viewModel: TimeLimitControlViewModel
): View() {

    override val root =
        vbox(alignment = Pos.CENTER) {
            label(text = "여유시간 설정")
            hbox(alignment = Pos.CENTER) {
                textfield(viewModel.timerInput) {
                    filterInput {
                        viewModel.changeButtonEnableState(false)
                        val change = it.controlNewText
                        if (change.isNullOrEmpty()) return@filterInput false
                        if (!change.isInt()) { return@filterInput false }
                        val newInt = change.toInt()
                        if (newInt in 1..9) {
                            viewModel.changeButtonEnableState(true)
                            return@filterInput true
                        }
                        else false
                    }
                    enableWhen(viewModel.inputEnableState)
                    style {
                        prefWidth = 40.px
                    }
                }
                label("초") {
                    style {
                        prefWidth = 20.px
                    }
                }
                button {
                    action { viewModel.changeEnableState() }
                    enableWhen {
                        if (viewModel.inputEnableState.get()) {
                            return@enableWhen viewModel.buttonEnableState
                        } else return@enableWhen SimpleBooleanProperty(true)
                    }
                    textProperty().bind(
                        Bindings.createStringBinding(
                            {
                                if (viewModel.inputEnableState.get()) "확인"
                                else "수정"
                            }, viewModel.inputEnableState
                        )
                    )
                }
                style {
                    prefHeight = 40.px
                }
            }
            style {
                prefWidth = 120.px
            }
        }
}