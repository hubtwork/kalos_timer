package com.timer.model

import com.timer.selectable.SelectablePhase


sealed interface TimerType {
    val title: String
    val warningTime: Time

    data class Normal(
        override val title: String,
        val initialTime: Time,
        override val warningTime: Time,
    ): TimerType

    data class Selectable(
        override val title: String,
        val initialPhase: SelectablePhase,
        override val warningTime: Time,
        val selectors: List<SelectablePhase>
    ): TimerType
}