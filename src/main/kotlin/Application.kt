import style.MainTheme
import tornadofx.App
import tornadofx.launch
import ui.BasicTimerView
import ui.MainView


class MapleTimerApp: App(MainView::class, MainTheme::class)

fun main(args: Array<String>) {
    launch<MapleTimerApp>(args)
}