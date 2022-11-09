package com.timer

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/09
 *  github   : https://github.com/hubtwork
 */

/**
 * Generator interface for TimerType.
 * 1) Not applicable with Factory pattern ( boilerplate must followed )
 *  >> Cause each TimerType's properties are different,
 *  >> If factory pattern applied each creator function must have different parameters.
 */
interface TimerViewGenerator {
    /** create timer view applicable with current TimerType */
    fun createView(): TimerView
}