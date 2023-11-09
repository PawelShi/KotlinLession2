package ru.shisterov.lession2_javafx.entities


import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

open class FRect: IFigure {

    companion object{
        const val title = "Прямоугольник"
        const val startX:Double = 0.0
        const val startY:Double = 0.0
        const val startW:Double = 0.0
        const val startH:Double = 0.0
    }

    private val figure = Rectangle(startX, startY, startW, startH)

    override val shape: Shape
        get() = figure

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        figure.x = x
        figure.y = y
        figure.width = 0.0
        figure.height = 0.0
        figure.stroke = color
        figure.strokeWidth = width
        figure.fill = null
    }

    override fun firstPoint(x: Double, y: Double) {
        figure.x = x
        figure.y = y
    }

    override fun secondPoint(x: Double, y: Double) {
        val width = x - figure.x
        val height = y - figure.y
        figure.width = width
        figure.height = height
    }

    override val name: String
        get() = title

}

class RectCreator: ICreator {
    override fun create(): IFigure = FRect()
}