package ru.shisterov.lession2_javafx.entities

import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

class FCircle : IFigure {

    private var startX:Double = 0.0
    private var startY:Double = 0.0
    private var radX:Double = 0.0
    private var radY:Double = 0.0


    private val circle = Ellipse(startX, startY, radX, radY)

    override val shape: Shape
        get() = circle

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        println(" ИНИЦИАЛИЗАЦИ круга $x $y")

        startX = x
        startY = y
        circle.centerX = startX
        circle.centerY = startY
        circle.radiusX = radX
        circle.radiusY = radY
        circle.stroke = color
        circle.strokeWidth = width
        circle.fill = null
    }

    override fun firstPoint(x: Double, y: Double) {
        startX = x
        startY = y
        circle.centerX = startX
        circle.centerY = startY
    }

    override fun secondPoint(x: Double, y: Double) {

        radX = x - startX
        radY = y - startY
        circle.radiusX = radX
        circle.radiusY = radY

    }

    override val name: String
        get() = title

    companion object{
        const val title = "Эллипс"
    }
}


class CircleCreator: ICreator {
    override fun create(): IFigure = FCircle()
}