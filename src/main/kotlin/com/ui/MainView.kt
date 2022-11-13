package com.ui

import com.timer.normal.NormalTimerView
import com.timer.model.Time
import com.timer.model.TimerType
import com.timer.selectable.SelectablePhase
import com.timer.selectable.SelectableTimerView
import com.util.resource.sound.SoundType
import javafx.geometry.Pos
import tornadofx.*

class MainView: View() {
    private val timeLimitController: TimeLimitControlViewModel
    private val warningTime = Time(seconds = 5)

    init {
        timeLimitController = TimeLimitControlViewModel(initialWarningTime = warningTime)
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
                    run {
                        val param = TimerType.Normal(
                            title = "폭탄 타이머",
                            initialTime = Time(seconds = 10, millis = 500),
                            warningTime = warningTime,
                        )
                        add(NormalTimerView.create(
                            timerType = param,
                            soundType = SoundType.Bomb
                        ))
                    }
                    run {
                        val param = TimerType.Normal(
                            title = "트리거 타이머",
                            initialTime = Time(seconds = 15),
                            warningTime = warningTime,
                        )
                        add(NormalTimerView.create(
                            timerType = param,
                            soundType = SoundType.Trigger
                        ))
                    }
                }
                vbox {
                    run {
                        val param = TimerType.Normal(
                            title = "레이저 타이머",
                            initialTime = Time(seconds = 15),
                            warningTime = warningTime,
                        )
                        add(NormalTimerView.create(
                            timerType = param,
                            soundType = SoundType.Laser
                        ))
                    }
                    run {
                        val param = TimerType.Normal(
                            title = "암흑화살 타이머",
                            initialTime = Time(seconds = 15),
                            warningTime = warningTime,
                        )
                        add(NormalTimerView.create(
                            timerType = param,
                            soundType = SoundType.Arrow
                        ))
                    }
                }
                vbox {
                    run {
                        val breathTimerParam = TimerType.Selectable(
                            title = "브레스 타이머",
                            initialPhase = SelectablePhase.Breath.Phase1,
                            warningTime = warningTime,
                            selectors = SelectablePhase.Breath.values().toList()
                        )
                        add(SelectableTimerView.create(
                            timerType = breathTimerParam,
                            soundType = SoundType.Breath
                        ))
                    }
                }
                vbox {
                    run {
                        val param = TimerType.Normal(
                            title = "4간섭 타이머",
                            initialTime = Time(seconds = 6),
                            warningTime = warningTime,
                        )
                        add(NormalTimerView.create(
                            timerType = param,
                            soundType = SoundType.Interference
                        ))
                    }
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