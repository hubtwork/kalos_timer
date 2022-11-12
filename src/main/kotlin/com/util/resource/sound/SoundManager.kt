package com.util.resource.sound

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
    companion object {
        @Volatile private var instance: SoundManager? = null

        @JvmStatic fun initialize() {
            instance = SoundManager().also { instance = it }
        }
        @JvmStatic fun getInstance(): SoundManager =
            instance ?: synchronized(this) {
                instance ?: SoundManager().also {
                    instance = it
                }
            }
    }

    private val dispatcher = Dispatchers.IO

    fun play(type: SoundType) {
        CoroutineScope(dispatcher).launch {
            val sound = this::class.java.getResource(type.fileName)?.toExternalForm()
            MediaPlayer(Media(sound)).play()
        }
    }
}