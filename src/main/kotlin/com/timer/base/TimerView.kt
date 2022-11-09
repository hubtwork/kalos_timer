package com.timer.base

import tornadofx.View

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */
abstract class TimerView<VM: TimerViewModel>: View() {
    abstract val viewModel: VM

}