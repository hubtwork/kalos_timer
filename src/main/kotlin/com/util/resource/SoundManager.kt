package com.util.resource

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @project : maple_timer
 * created by
 *  @author  : alenheo on 2022/11/12
 *  github   : https://github.com/hubtwork
 */
class SoundManager {

    enum class SoundType(val fileName: String) {
        Bomb("/sound_bomb.wav"),
        Breath("/sound_breath.wav"),
        CC("/sound_cc.wav"),
        Laser("/sound_laser.wav");

        fun serveSound() = getInstance().play(this)
    }

    companion object {
        @Volatile private var instance: SoundManager? = null

        @JvmStatic fun getInstance(): SoundManager =
            instance ?: synchronized(this) {
                instance ?: SoundManager().also {
                    instance = it
                }
            }
    }

    private val dispatcher = Dispatchers.IO

    private fun play(type: SoundType) {
        CoroutineScope(dispatcher).launch {
            val sound = this::class.java.getResource(type.fileName)?.toExternalForm()
            MediaPlayer(Media(sound)).play()
        }
    }
}