package ru.shisterov.lession2_javafx.entities

import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

class FCircle : IFigure {

    // TODO: Т.к. figure повторяется во всех классах, нужно наследовать, а не интерфейс

    private val figure = Ellipse(startX, startY, startRadX, startRadY)

    override val shape: Shape
        get() = figure

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        println(" ИНИЦИАЛИЗАЦИ круга $x $y")
        figure.centerX = x
        figure.centerY = y
        figure.radiusX = startRadX
        figure.radiusY = startRadY
        figure.stroke = color
        figure.strokeWidth = width
        figure.fill = null
    }

    override fun firstPoint(x: Double, y: Double) {

        figure.centerX = x
        figure.centerY = y
    }

    override fun secondPoint(x: Double, y: Double) {

        figure.radiusX = x - figure.centerX
        figure.radiusY = y - figure.centerY

    }

    override val name: String
        get() = title

    companion object{
        const val title = "Эллипс"
        const val startX:Double = 0.0
        const val startY:Double = 0.0
        const val startRadX:Double = 0.0
        const val startRadY:Double = 0.0
    }
}


class CircleCreator: ICreator {
    override fun create(): IFigure = FCircle()
}