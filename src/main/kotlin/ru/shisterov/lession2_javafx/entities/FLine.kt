package ru.shisterov.lession2_javafx.entities

import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

open class FLine (): IFigure {
    companion object{
        const val TITLE = "Линия"
        private const val START_X = 0.0
        private const val START_Y = 0.0
        private const val END_X = 0.0
        private const val END_Y = 0.0
    }


    private val figure = Line(START_X, START_Y, END_X, END_Y)

    override val shape: Shape
        get() = figure

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        with(figure) {
            startX = x
            startY = y
            endX = x
            endY = y
            stroke = color
            strokeWidth = width
        }
    }

    override fun firstPoint(x: Double, y: Double) {
        with(figure) {
            startX = x
            startY = y
        }
    }

    override fun secondPoint(x: Double, y: Double) {
        with(figure) {
            endX = x
            endY = y
        }
    }


    override val name: String
        get() = TITLE


}

class LineCreator: ICreator {
    override fun create(): IFigure = FLine()
}