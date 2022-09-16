package style

import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.px

class MainTheme: Stylesheet() {
    companion object {
        val heading by cssclass()
    }

    init {
        initLabelBindings()
    }

    private fun initLabelBindings() {
        label.apply {
            this and heading {
                fontSize = 20.px
            }
        }
    }
}