package com.style

import tornadofx.*

class MainTheme: Stylesheet() {
    companion object {
        val heading by cssclass()
        val timerContainer by cssclass()
        val redBod by cssclass()
        val blackBod by cssclass()
    }

    init {
        initLabelBindings()
        initBoxBindings()
    }

    private fun initLabelBindings() {
        label.apply {
            this and heading {
                fontSize = 20.px
            }
        }
    }

    private fun initBoxBindings() {
        timerContainer {
            minWidth = 120.px
            prefWidth = 120.px
            minHeight = 60.px
            prefHeight = 60.px
        }
        redBod {
            borderColor += box(c("#FF0000"))
        }
        blackBod {
            borderColor += box(c("#171717"))
        }
    }
}