package com.timer.model

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */
data class Time(
    val seconds: Long,
    val millis: Long = 0L,
) {
    val timeMillis: Long = seconds * 1000 + millis
}
