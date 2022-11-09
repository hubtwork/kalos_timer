package com.timer

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlin.concurrent.thread

fun serveTimeline(
    eventHandler: EventHandler<ActionEvent>
): Timeline {
    return Timeline(
        KeyFrame(
            Duration.millis(1.0),
            eventHandler
        )
    ).apply {
        cycleCount = Animation.INDEFINITE
    }
}

class SoundPack private constructor() {

    companion object {
        @Volatile private var instance: SoundPack? = null

        @JvmStatic fun initialize() {
            instance = SoundPack()
        }

        @JvmStatic fun getInstance(): SoundPack =
            instance ?: synchronized(this) {
                instance ?: SoundPack().also {
                    instance = it
                }
            }
    }

    private val sound: String? = SoundPack::class.java.getResource("/sound.wav")?.toExternalForm()

    fun serveSound() {
        thread {
            MediaPlayer(Media(sound)).play()
        }.join()
    }
}