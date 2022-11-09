package com.timer


sealed interface TimerType {
    val title: Long
    val seconds: Long

    data class Normal(
        override val title: Long,
        override val seconds: Long,
        val millis: Long = 0L,
        val warningTime: Long = 5000L,
    ): TimerType {
        val timeMillis: Long get() = seconds * 1000 + millis
    }

}