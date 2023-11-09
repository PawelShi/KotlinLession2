package ru.shisterov.lession2_javafx.entities


import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

open class FRect: IFigure {

    companion object{
        const val TITLE = "Прямоугольник"
        private const val START_X = 0.0
        private const val START_Y = 0.0
        private const val START_W = 0.0
        private const val START_H = 0.0
    }

    private val figure = Rectangle(START_X, START_Y, START_W, START_H)

    override val shape: Shape
        get() = figure

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        with(figure) {
            this.x = x
            this.y = y
            this.width = 0.0
            height = 0.0
            stroke = color
            strokeWidth = width
            fill = null
        }
    }

    override fun firstPoint(x: Double, y: Double) {
        figure.x = x
        figure.y = y
    }

    override fun secondPoint(x: Double, y: Double) {
        val w = x - figure.x
        val h = y - figure.y
        with(figure) {
            width = w
            height = h
        }
    }

    override val name: String
        get() = TITLE

}

class RectCreator: ICreator {
    override fun create(): IFigure = FRect()
}