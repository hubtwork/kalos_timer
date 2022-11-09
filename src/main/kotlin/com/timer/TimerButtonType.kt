package com.timer

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */
sealed interface TimerButtonType {
    enum class Basic: TimerButtonType {
        StartStop, Reset;
    }
    data class ChangeTime(val time: Time): TimerButtonType
}