package com.ui

import com.timer.normal.NormalTimerView
import com.timer.model.Time
import javafx.geometry.Pos
import javafx.scene.control.RadioButton
import tornadofx.*

class MainView: View() {
    private val timeLimitController: TimeLimitControlViewModel
    private val boomTimer: NormalTimerView
    private val triggerTimer: NormalTimerView
    private val laserTimer: NormalTimerView
    private val weaknessTimer: NormalTimerView
    private val ccTimer: NormalTimerView

    init {
        val warningSecond: Long = 5
        timeLimitController = TimeLimitControlViewModel(initialWarningTime = warningSecond)
        boomTimer = NormalTimerView.create(
            title = "폭탄 타이머",
            initialTime = Time(seconds = 10, millis = 500),
            warningSecond = warningSecond,
        )
        triggerTimer = NormalTimerView.create(
            title = "트리거 타이머",
            initialTime = Time(seconds = 15),
            warningSecond = warningSecond,
        )
        laserTimer = NormalTimerView.create(
            title = "레이저 타이머",
            initialTime = Time(seconds = 15),
            warningSecond = warningSecond,
        )
        weaknessTimer = NormalTimerView.create(
            title = "허약 타이머",
            initialTime = Time(seconds = 20),
            warningSecond = warningSecond,
        )
        ccTimer = NormalTimerView.create(
            title = "4간섭 타이머",
            initialTime = Time(seconds = 60),
            warningSecond = warningSecond,
        )

        RadioButton().setOnAction {  }
    }


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