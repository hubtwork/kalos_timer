package com.ui

import com.components.normal.NormalTimerView
import com.timer.Time
import com.timer.TimerType
import javafx.geometry.Pos
import tornadofx.*

class MainView: View() {
    private val boomTimer: NormalTimerView
    init {
        boomTimer = NormalTimerView.create(
            param = TimerType.Normal(
                title = "폭탄 타이머",
                initialTime = Time(seconds = 10, millis = 500),
                warningTime = Time(seconds = 5)
            )
        )
    }
    val timeLimitController = TimeLimitControlViewModel()

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