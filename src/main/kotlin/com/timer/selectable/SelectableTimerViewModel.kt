package com.timer.selectable

import com.style.MainTheme
import com.timer.base.TimerViewModel
import com.timer.model.TimerActionType
import com.timer.model.TimerType
import javafx.beans.property.SimpleObjectProperty
import tornadofx.getValue
import tornadofx.setValue

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/12
 *  github   : https://github.com/hubtwork
 */
class SelectableTimerViewModel(
    param: TimerType.Selectable
): TimerViewModel(type = param) {
    // UI Properties
    val timerBorderProps = SimpleObjectProperty(MainTheme.blackBod)
    private var uiTimerBorder by timerBorderProps
    val phaseProps = SimpleObjectProperty<SelectablePhase>(param.initialPhase)
    private var currentPhase by phaseProps

    fun selectTimer(phase: SelectablePhase) {
        currentPhase = phase
        setTime = phase.time.timeMillis
        onClickTimerButton(TimerActionType.Basic.Reset)
    }

    override fun onUiWarning() {
        super.onUiWarning()
        uiTimerBorder = MainTheme.redBod
    }

    override fun onUiNormal() {
        uiTimerBorder = MainTheme.blackBod
    }
}