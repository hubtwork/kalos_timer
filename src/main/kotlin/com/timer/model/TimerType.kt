package com.timer.model


sealed interface TimerType {
    val title: String
    val initialTime: Time
    val warningTime: Time

    data class Normal(
        override val title: String,
        override val initialTime: Time,
        override val warningTime: Time,
    ): TimerType

    data class Selectable(
        override val title: String,
        override val initialTime: Time,
        override val warningTime: Time,
        val selectors: List<Time>
    ): TimerType
}