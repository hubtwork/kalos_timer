package com.ui

import com.style.MainTheme
import javafx.beans.binding.Bindings
import javafx.beans.property.LongProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.text.Font
import tornadofx.*

enum class CC_Type(val time: Int) {
    Bind(10),
    CriticalBind(4),
    Pressure(30),
    Groggy(20),
    ServerDelay(1),
}

class MissileTimerController: ViewModel() {


    val startMinuteEnableState = SimpleBooleanProperty(false)
    fun changeMinuteState(state: Boolean) {
        startMinuteEnableState.set(state)
        if (startMinuteEnableState.get() && startSecondEnableState.get()) buttonEnableState.set(true)
        else buttonEnableState.set(true)
    }
    val startSecondEnableState = SimpleBooleanProperty(false)
    fun changeSecondState(state: Boolean) {
        startSecondEnableState.set(state)
        if (startMinuteEnableState.get() && startSecondEnableState.get()) buttonEnableState.set(true)
        else buttonEnableState.set(true)
    }
    val inputEnableState = SimpleBooleanProperty(false)
    fun changeEnableState() {
        if (startMinute.isNullOrEmpty() || startSecond.isNullOrEmpty()) return
        val current = inputEnableState.get()
        if (current) calcEndTime()
        inputEnableState.set(!current)
    }
    val buttonEnableState = SimpleBooleanProperty(true)
    fun changeButtonEnableState(state: Boolean) {
        buttonEnableState.set(state)
    }
    fun calcEndTime() {
        val totalTime = startMinute.toInt() * 60 + startSecond.toInt()
        val endTime = if (totalTime - 150 > 0) totalTime - 150 else 0

        endMinuteProperty.set("%02d".format(endTime / 60))
        endSecondProperty.set("%02d".format(endTime % 60))
        Count_Bind.set(0)
        Count_CriticalBind.set(0)
        Count_Pressure.set(0)
        Count_Groggy.set(0)
        Count_ServerDelay.set(0)
    }
    fun endTimeCalculator(isAdd: Boolean, amount: Int) {
        val origin = endMinute.toInt() * 60 + endSecond.toInt()
        val next = origin + amount * if (isAdd) -1 else 1
        val endTime = if (next > 0) next else 0
        endMinuteProperty.set("%02d".format(endTime / 60))
        endSecondProperty.set("%02d".format(endTime % 60))
    }

    fun onButtonClick(isAdd: Boolean, type: CC_Type) {
        val origin = endMinute.toInt() * 60 + endSecond.toInt()
        val counter = when(type) {
            CC_Type.Bind -> Count_Bind.get()
            CC_Type.CriticalBind -> Count_CriticalBind.get()
            CC_Type.Pressure -> Count_Pressure.get()
            CC_Type.Groggy -> Count_Groggy.get()
            CC_Type.ServerDelay -> Count_ServerDelay.get()
        }
        if (isAdd) {
            if (origin == 0) return
            val _next = origin - type.time
            val next = if (_next > 0) _next else 0
            when(type) {
                CC_Type.Bind -> Count_Bind += 1
                CC_Type.CriticalBind -> Count_CriticalBind += 1
                CC_Type.Pressure -> Count_Pressure += 1
                CC_Type.Groggy -> Count_Groggy += 1
                CC_Type.ServerDelay -> Count_ServerDelay += 1
            }
            endMinuteProperty.set("%02d".format(next / 60))
            endSecondProperty.set("%02d".format(next % 60))
        } else {
            if (counter == 0L) return
            val next = origin + type.time
            when(type) {
                CC_Type.Bind -> Count_Bind -= 1
                CC_Type.CriticalBind -> Count_CriticalBind -= 1
                CC_Type.Pressure -> Count_Pressure -= 1
                CC_Type.Groggy -> Count_Groggy -= 1
                CC_Type.ServerDelay -> Count_ServerDelay -= 1
            }
            endMinuteProperty.set("%02d".format(next / 60))
            endSecondProperty.set("%02d".format(next % 60))
        }
    }

    fun syncEndTime() {
        startMinuteProperty.set(endMinute)
        startSecondProperty.set(endSecond)
        calcEndTime()
    }

    val startMinuteProperty = SimpleStringProperty("25")
    val startSecondProperty = SimpleStringProperty("00")
    var startMinute by startMinuteProperty
    var startSecond by startSecondProperty

    val endMinuteProperty = SimpleStringProperty()
    val endSecondProperty = SimpleStringProperty()
    var endMinute by endMinuteProperty
    var endSecond by endSecondProperty

    val Count_Bind = SimpleLongProperty(0)
    val Count_CriticalBind = SimpleLongProperty(0)
    val Count_Pressure = SimpleLongProperty(0)
    val Count_Groggy = SimpleLongProperty(0)
    val Count_ServerDelay = SimpleLongProperty(0)

}

class MissileTimer(
    private val viewModel: MissileTimerController = MissileTimerController()
): View() {
    init {
        viewModel.calcEndTime()
    }
    override val root =
            vbox(alignment = Pos.CENTER) {
                label("즉사 타이머") {
                    style { prefHeight = 30.px }
                }
                hbox(alignment = Pos.CENTER) {
                    vbox(alignment = Pos.CENTER) {
                        label("입장 / 사용 시간")
                        hbox(alignment = Pos.CENTER) {
                            textfield(viewModel.startMinuteProperty) {
                                filterInput {
                                    viewModel.changeButtonEnableState(false)
                                    val change = it.controlNewText
                                    if (change.isNullOrEmpty()) return@filterInput false
                                    if (!change.isInt()) { return@filterInput false }
                                    val newInt = change.toInt()
                                    if (newInt in 0..59 && change.length < 3) {
                                        viewModel.changeMinuteState(true)
                                        return@filterInput true
                                    }
                                    else {
                                        viewModel.changeMinuteState(false)
                                        return@filterInput false
                                    }
                                }
                                enableWhen(viewModel.inputEnableState)

                                style {
                                    prefWidth = 40.px
                                }
                            }
                            label(" : ")
                            textfield(viewModel.startSecondProperty) {
                                filterInput {
                                    viewModel.changeButtonEnableState(false)
                                    val change = it.controlNewText
                                    if (change.isNullOrEmpty()) return@filterInput false
                                    if (!change.isInt()) { return@filterInput false }
                                    val newInt = change.toInt()
                                    if (newInt in 0..59 && change.length < 3) {
                                        viewModel.changeSecondState(true)
                                        return@filterInput true
                                    }
                                    else {
                                        viewModel.changeSecondState(false)
                                        return@filterInput false
                                    }
                                }
                                enableWhen(viewModel.inputEnableState)

                                style {
                                    prefWidth = 40.px
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
                                prefWidth = 300.px
                                prefHeight = 60.px
                            }
                            addClass(MainTheme.blackBod)
                        }
                    }
                    vbox(alignment = Pos.CENTER) {
                        label("다음 예상 시간")
                        hbox(alignment = Pos.CENTER) {
                            hbox(alignment = Pos.CENTER) {
                                text {
                                    bind(viewModel.endMinuteProperty)
                                    style {
                                        prefWidth = 40.px
                                    }
                                }
                                label(" : ")
                                text {
                                    bind(viewModel.endSecondProperty)
                                    style {
                                        prefWidth = 60.px
                                        minWidth = 60.px
                                    }
                                }
                                style {
                                    prefWidth = 100.px
                                    minWidth = 100.px
                                }
                            }
                            button("동기화") {
                                action { viewModel.syncEndTime() }

                            }

                            style {
                                prefWidth = 300.px
                                prefHeight = 60.px
                            }
                            addClass(MainTheme.blackBod)
                        }
                    }
                }
                hbox(alignment = Pos.CENTER) {
                    vbox(alignment = Pos.TOP_CENTER) {
                        hbox(alignment = Pos.CENTER_LEFT) {
                            label(text = "바인드 (10초)") { style { prefWidth = 140.px } }
                            button("+") { action { viewModel.onButtonClick(true, CC_Type.Bind) } }
                            label { style { prefWidth = 5.px } }
                            button("-") {
                                action { viewModel.onButtonClick(false, CC_Type.Bind) }
                            }
                            text {
                                textProperty().bind(
                                    Bindings.createStringBinding(
                                        {
                                            "  ( ${viewModel.Count_Bind.get()} )"
                                        }, viewModel.Count_Bind
                                    )
                                )
                                style {
                                    prefWidth = 40.px
                                    alignment = Pos.CENTER
                                }
                            }
                            style { prefHeight = 40.px }
                        }
                        hbox(alignment = Pos.CENTER_LEFT) {
                            hbox(alignment = Pos.CENTER_LEFT) {
                                vbox(alignment = Pos.CENTER_LEFT) {
                                    label(text = "크리티컬바인드")
                                    label(text = "쉐도우레인")
                                }
                                label(text = "  (4초)")
                                style { prefWidth = 140.px }
                            }
                            button("+") { action { viewModel.onButtonClick(true, CC_Type.CriticalBind) } }
                            label { style { prefWidth = 5.px } }
                            button("-") { action { viewModel.onButtonClick(false, CC_Type.CriticalBind) } }
                            text {
                                textProperty().bind(
                                    Bindings.createStringBinding(
                                        {
                                            "  ( ${viewModel.Count_CriticalBind.get()} )"
                                        }, viewModel.Count_CriticalBind
                                    )
                                ) }
                            style { prefHeight = 40.px }
                        }
                        hbox(alignment = Pos.CENTER_LEFT) {
                            label(text = "권능 (30초)") { style { prefWidth = 140.px } }
                            button("+") { action { viewModel.onButtonClick(true, CC_Type.Pressure) } }
                            label { style { prefWidth = 5.px } }
                            button("-") { action { viewModel.onButtonClick(false, CC_Type.Pressure) } }
                            text {
                                textProperty().bind(
                                    Bindings.createStringBinding(
                                        {
                                            "  ( ${viewModel.Count_Pressure.get()} )"
                                        }, viewModel.Count_Pressure
                                    )
                                ) }
                            style { prefHeight = 40.px }
                        }
                        hbox(alignment = Pos.CENTER_LEFT) {
                            label(text = "그로기 (20초)") { style { prefWidth = 140.px } }
                            button("+") { action { viewModel.onButtonClick(true, CC_Type.Groggy) } }
                            label { style { prefWidth = 5.px } }
                            button("-") { action { viewModel.onButtonClick(false, CC_Type.Groggy) } }
                            text {
                                textProperty().bind(
                                    Bindings.createStringBinding(
                                        {
                                            "  ( ${viewModel.Count_Groggy.get()} )"
                                        }, viewModel.Count_Groggy
                                    )
                                ) }
                            style { prefHeight = 40.px }
                        }
                        hbox(alignment = Pos.CENTER_LEFT) {
                            label(text = "서버렉 (1초)") { style { prefWidth = 140.px } }
                            button("+") { action { viewModel.onButtonClick(true, CC_Type.ServerDelay) } }
                            label { style { prefWidth = 5.px } }
                            button("-") { action { viewModel.onButtonClick(false, CC_Type.ServerDelay) } }
                            text {
                                textProperty().bind(
                                    Bindings.createStringBinding(
                                        {
                                            "  ( ${viewModel.Count_ServerDelay.get()} )"
                                        }, viewModel.Count_ServerDelay
                                    )
                                )
                            }
                            style { prefHeight = 40.px }
                        }
                        style {
                            prefWidth = 400.px
                        }
                    }
                    vbox(alignment = Pos.TOP_CENTER) {
                        hbox(alignment = Pos.CENTER) {
                            button("리셋") {
                                action { viewModel.calcEndTime() }
                                style { prefWidth = 55.px }
                            }
                            style { prefHeight = 40.px }
                        }
                        style {
                            prefWidth = 200.px
                        }
                    }
                }
                style {
                    prefWidth = 440.px
                }
            }
}