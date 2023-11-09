package ru.shisterov.lession2_javafx.entities

import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

class FCircle : IFigure {

    companion object{
        const val TITLE = "Эллипс"
        private const val START_X = 0.0
        private const val START_Y = 0.0
        private const val START_RAD_X = 0.0
        private const val START_RAD_Y = 0.0
    }
    // TODO: Т.к. figure повторяется во всех классах, нужно наследовать, а не интерфейс

    private val figure = Ellipse(START_X, START_Y, START_RAD_X, START_RAD_Y)

    override val shape: Shape
        get() = figure

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        println(" ИНИЦИАЛИЗАЦИ круга $x $y")
        with(figure) {
            centerX = x
            centerY = y
            radiusX = START_RAD_X
            radiusY = START_RAD_Y
            stroke = color
            strokeWidth = width
            fill = null
        }
    }

    override fun firstPoint(x: Double, y: Double) {

        with(figure) {
            centerX = x
            centerY = y
        }
    }

    override fun secondPoint(x: Double, y: Double) {
        with(figure) {
            radiusX = x - centerX
            radiusY = y - centerY
        }
    }

    override val name: String
        get() = TITLE

}


class CircleCreator: ICreator {
    override fun create(): IFigure = FCircle()
}