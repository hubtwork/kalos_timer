package com.util.resource.sound

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/12
 *  github   : https://github.com/hubtwork
 */
class SoundPlayer(
    private val type: SoundType
): OnSoundPlayListener {
    override fun playSound() {
        SoundManager.getInstance().play(type)
    }
}