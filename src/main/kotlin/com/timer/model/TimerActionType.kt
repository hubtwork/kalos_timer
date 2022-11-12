package com.timer.model

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */
sealed interface TimerActionType {
    enum class Basic: TimerActionType {
        StartStop, Reset;
    }
    data class ChangeTime(val time: Time): TimerActionType
}