package com.timer.selectable

import com.style.MainTheme
import com.timer.base.TimerView
import com.timer.model.TimerActionType
import com.timer.model.TimerType
import com.util.resource.sound.SoundPlayer
import com.util.resource.sound.SoundType
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/12
 *  github   : https://github.com/hubtwork
 */
class SelectableTimerView(
    override val viewModel: SelectableTimerViewModel,
    private val selectors: List<SelectablePhase>
): TimerView<SelectableTimerViewModel>() {

    companion object {
        private const val TAG_ToggleGroup = "PhaseToggleGroup"

        fun create(
            timerType: TimerType.Selectable,
            soundType: SoundType? = null,
        ): SelectableTimerView {
            val vm = SelectableTimerViewModel(type = timerType)
            soundType?.let {
                vm.registerOnSoundPlayListener(_listener = SoundPlayer(it))
            }
            return SelectableTimerView(
                viewModel = vm,
                selectors = timerType.selectors,
            )
        }
    }

    init {
        viewModel.phaseProps.addListener { _, _, newValue ->
            if (newValue != null) viewModel.selectTimer(newValue)
        }

    }

    override val root =
        hbox {
            vbox(alignment = Pos.CENTER) {
                label(viewModel.title) {
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
                                    val time = viewModel.timeProps.get()
                                    String.format(
                                        "%02d:%02d",
                                        time / 1000,
                                        time % 1000 / 10,
                                    )
                                }, viewModel.timeProps
                            )
                        )
                        style {
                            alignment = Pos.BASELINE_CENTER
                        }
                    }

                    addClass(MainTheme.timerContainer)
                    bindClass(viewModel.timerBorderProps)
                }
                hbox {
                    button(text = "시작") {
                        action { viewModel.onClickTimerButton(type = TimerActionType.Basic.StartStop) }
                        style {
                            prefWidth = 60.px
                        }
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    if (viewModel.onPlayingProps.get()) "정지"
                                    else "시작"
                                }, viewModel.onPlayingProps
                            )
                        )
                    }
                    button(text = "리셋") {
                        action { viewModel.onClickTimerButton(type = TimerActionType.Basic.Reset) }
                        style {
                            prefWidth = 60.px
                        }
                    }
                }
                togglegroup {
                    id = TAG_ToggleGroup
                    run {
                        selectors.forEach { phase ->
                            radiobutton(phase.label, value = phase) {
                                style {
                                    paddingTop = 10
                                }
                            }
                        }
                    }
                    selectedValueProperty<SelectablePhase>().bindBidirectional(viewModel.phaseProps)
                }
            }

            style {
                padding = box(20.px)
            }
        }
}