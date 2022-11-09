package com.components

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.util.Duration

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