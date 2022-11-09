package com.components.normal

import com.style.MainTheme
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
    title: String,
    private val viewModel: NormalTimerViewModel
): View() {

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
                                    val time = viewModel.timeProps
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
                    bindClass(viewModel._uiTimerBorder)
                }
                hbox {
                    button(text = "시작") {
                        action { viewModel.onClickTimerButton(type = NormalTimerButtonType.StartStop) }
                        style {
                            prefWidth = 60.px
                        }
                        textProperty().bind(
                            Bindings.createStringBinding(
                                {
                                    if (viewModel.isOnTicking.get()) "정지"
                                    else "시작"
                                }, viewModel.isOnTicking
                            )
                        )
                    }
                    button(text = "리셋") {
                        action { viewModel.onClickTimerButton(type = NormalTimerButtonType.Reset) }
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