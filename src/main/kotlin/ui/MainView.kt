package ui

import javafx.scene.Parent
import tornadofx.*

class MainView: View() {
    val timeLimitController = TimeLimitControlViewModel()

    val boomTimer = BasicTimerView("폭탄 타이머", 10)
    val triggerTimer = BasicTimerView("트리거 타이머", 15)
    val laserTimer = BasicTimerView("레이저 타이머", 15)
    val weaknessTimer = BasicTimerView("허약 타이머", 20)
    override val root =
        hbox {
            vbox {
                add(boomTimer)
                add(triggerTimer)
            }
            vbox {
                add(laserTimer)
                add(weaknessTimer)
            }
            add(TimeLimitControlView(viewModel = timeLimitController))
            add(SelectableTimerView("브레스 타이머"))
        }
}