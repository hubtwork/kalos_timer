package com.ui

import javafx.geometry.Pos
import tornadofx.*

class MainView: View() {
    val timeLimitController = TimeLimitControlViewModel()

    val boomTimer = BasicTimerView("폭탄 타이머", 10)
    val triggerTimer = BasicTimerView("트리거 타이머", 15)
    val laserTimer = BasicTimerView("레이저 타이머", 15)
    val weaknessTimer = BasicTimerView("허약 타이머", 20)
    val ccTimer = BasicTimerView("4간섭 타이머", 60)

    override val root =
        vbox {
            hbox(alignment = Pos.CENTER) {
                add(TimeLimitControlView(viewModel = timeLimitController))

                style {
                    prefHeight = 80.px
                    minHeight = 80.px
                }
            }
            hbox(alignment = Pos.TOP_CENTER) {
                vbox {
                    add(boomTimer)
                    add(triggerTimer)
                }
                vbox {
                    add(laserTimer)
                    add(weaknessTimer)
                }
                vbox {
                    add(SelectableTimerView("브레스 타이머"))
                }
                vbox {
                    add(ccTimer)
                }
                add(MissileTimer())
            }
        }
}