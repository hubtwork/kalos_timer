package com.ui

import com.timer.normal.NormalTimerView
import com.timer.model.Time
import com.timer.model.TimerType
import com.timer.selectable.SelectablePhase
import com.timer.selectable.SelectableTimerView
import com.util.resource.sound.SoundType
import javafx.geometry.Pos
import javafx.scene.control.RadioButton
import tornadofx.*

class MainView: View() {
    private val timeLimitController: TimeLimitControlViewModel
    private val boomTimer: NormalTimerView
    private val triggerTimer: NormalTimerView
    private val laserTimer: NormalTimerView
    private val arrowTimer: NormalTimerView
    private val ccTimer: NormalTimerView

    private val warningSecond: Long = 5

    init {
        timeLimitController = TimeLimitControlViewModel(initialWarningTime = warningSecond)
        boomTimer = NormalTimerView.create(
            title = "폭탄 타이머",
            initialTime = Time(seconds = 10, millis = 500),
            warningSecond = warningSecond,
            type = SoundType.Bomb,
        )
        triggerTimer = NormalTimerView.create(
            title = "트리거 타이머",
            initialTime = Time(seconds = 15),
            warningSecond = warningSecond,
            type = SoundType.Trigger,
        )
        laserTimer = NormalTimerView.create(
            title = "레이저 타이머",
            initialTime = Time(seconds = 15),
            warningSecond = warningSecond,
            type = SoundType.Laser,
        )
        arrowTimer = NormalTimerView.create(
            title = "암흑화살 타이머",
            initialTime = Time(seconds = 15),
            warningSecond = warningSecond,
            type = SoundType.Arrow,
        )
        ccTimer = NormalTimerView.create(
            title = "4간섭 타이머",
            initialTime = Time(seconds = 60),
            warningSecond = warningSecond,
            type = SoundType.CC,
        )

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
                    add(arrowTimer)
                }
                vbox {
                    run {
                        val breathTimerParam = TimerType.Selectable(
                            title = "브레스 타이머",
                            initialPhase = SelectablePhase.Breath.Phase1,
                            warningTime = Time(seconds = warningSecond),
                            selectors = SelectablePhase.Breath.values().toList()
                        )
                        add(SelectableTimerView.create(
                            timerType = breathTimerParam,
                            soundType = SoundType.Breath
                        ))
                    }
                }
                vbox {
                    add(ccTimer)
                }
                add(MissileTimer())
            }
            hbox(alignment = Pos.BOTTOM_RIGHT) {
                vbox(alignment = Pos.CENTER_LEFT) {
                    label(text = "개발: hubtwork ( hubtwork@gmail.com )")
                    label(text = "기획: TopXenonSoon, Ensillia")
                }
                style {
                    paddingTop = 20
                    fontSize = 15.px
                }
            }
            style {
                paddingRight = 20
                paddingBottom = 20
            }
        }
}