package ui

import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.text.Font
import style.MainTheme
import tornadofx.*

class MissileTimer: View() {
    override val root: Parent
        get() =
            vbox {
                hbox {
                    vbox(alignment = Pos.CENTER) {
                        label("입장 / 사용 시간")
                        vbox(alignment = Pos.CENTER) {
                            text{
                                font = Font(20.0)
                                style {
                                    alignment = Pos.BASELINE_CENTER
                                }
                            }
                            addClass(MainTheme.timerContainer)
                            addClass(MainTheme.blackBod)
                        }
                        addClass(MainTheme.timerContainer)
                    }
                }
                hbox {

                }
            }
}