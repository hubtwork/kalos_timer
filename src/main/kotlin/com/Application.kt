package com
import com.style.MainTheme
import com.timer.SoundPack
import com.ui.MainView
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch


class MapleTimerApp: App(MainView::class, MainTheme::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.titleProperty().unbind()
        stage.title = "그루터기 칼로스 타이머 v0.1.0"
        stage.isResizable = false
    }
}

fun main(args: Array<String>) {
    SoundPack.initialize()
    launch<MapleTimerApp>(args)
}