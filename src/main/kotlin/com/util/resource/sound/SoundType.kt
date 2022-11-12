package com.util.resource.sound

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/12
 *  github   : https://github.com/hubtwork
 */


enum class SoundType(val fileName: String) {
    Bomb("/sound_bomb.wav"),
    Breath("/sound_breath.wav"),
    CC("/sound_cc.mp3"),
    Laser("/sound_laser.wav");
}
