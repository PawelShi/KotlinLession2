package ru.shisterov.lession2_javafx.entities

import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

open class FLine (): IFigure {

    private val line = Line(startX, startY, endX, endY)

    override val shape: Shape
        get() = line

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        line.startX = x
        line.startY = y
        line.endX = x
        line.endY = y
        line.stroke = color
        line.strokeWidth = width
    }

    override fun firstPoint(x: Double, y: Double) {
        line.startX = x
        line.startY = y
    }

    override fun secondPoint(x: Double, y: Double) {
        line.endX = x
        line.endY = y
    }


    override val name: String
        get() = title

    companion object{
        const val title = "Линия"
        const val startX:Double = 0.0
        const val startY:Double = 0.0
        const val endX:Double = 0.0
        const val endY:Double = 0.0
    }
}

class LineCreator: ICreator {
    override fun create(): IFigure = FLine()
}