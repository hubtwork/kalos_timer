package com.timer.normal

import com.style.MainTheme
import com.timer.model.TimerActionType
import com.timer.model.TimerType
import com.timer.base.TimerView
import com.util.resource.sound.SoundPlayer
import com.util.resource.sound.SoundType
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */

class NormalTimerView(
    override val viewModel: NormalTimerViewModel
): TimerView<NormalTimerViewModel>() {
    companion object {
        fun create(
            timerType: TimerType.Normal,
            soundType: SoundType? = null,
        ): NormalTimerView {
            val vm = NormalTimerViewModel(type = timerType)
            soundType?.let {
                vm.registerOnSoundPlayListener(_listener = SoundPlayer(it))
            }
            return NormalTimerView(
                viewModel = vm
            )
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
                    text{
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
                    button(text = "??????") {
                        action { viewModel.onClickTimerButton(type = TimerActionType.Basic.StartStop) }
                        style {
                            prefWidth = 60.px
                        }
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    if (viewModel.onPlayingProps.get()) "??????"
                                    else "??????"
                                }, viewModel.onPlayingProps
                            )
                        )
                    }
                    button(text = "??????") {
                        action { viewModel.onClickTimerButton(type = TimerActionType.Basic.Reset) }
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