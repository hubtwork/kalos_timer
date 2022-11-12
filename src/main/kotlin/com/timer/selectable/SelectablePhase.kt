package com.timer.selectable

import com.timer.model.Time

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/12
 *  github   : https://github.com/hubtwork
 */
sealed interface SelectablePhase {
    val label: String
    val time: Time

    enum class Breath(
        override val label: String,
        override val time: Time
    ): SelectablePhase {
        Phase1("2-1 (60)", Time(60)),
        Phase2("2-2 (45)", Time(45)),
        Phase3("2-3 (30)", Time(30))
    }

}