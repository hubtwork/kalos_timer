package com
import com.style.MainTheme
import com.ui.MainView
import com.util.resource.sound.SoundManager
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch


class MapleTimerApp: App(MainView::class, MainTheme::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.titleProperty().unbind()
        stage.title = "Kalos Complete Timer v0.2.0 < MapleStory KR, Union Server - 그루터기 >"
        stage.isResizable = false
    }
}

fun main(args: Array<String>) {
    // Init Managers
    SoundManager.initialize()
    // Start App
    launch<MapleTimerApp>(args)
}