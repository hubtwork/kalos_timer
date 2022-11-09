package com.model

data class TimerParam(
    val seconds: Long,
    val millis: Long = 0,
    val warningTime: Long = 5000L,
) {
    val timeMillis: Long get() = seconds * 1000 + millis
}