package ru.shisterov.lession2_javafx.entities


import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.creators.ICreator

open class FRect (): IFigure {

    private val rect:Rectangle = Rectangle(startX, startY, startW, startH)

    override val shape: Shape
        get() = rect

    override fun init(x: Double, y: Double, color: Color, width: Double) {
        rect.x = x
        rect.y = y
        rect.width = 0.0
        rect.height = 0.0
        rect.stroke = color
        rect.strokeWidth = width
        rect.fill = null
    }

    override fun firstPoint(x: Double, y: Double) {
        rect.x = x
        rect.y = y
    }

    override fun secondPoint(x: Double, y: Double) {
        val width = x - rect.x
        val height = y - rect.y
        rect.width = width
        rect.height = height
    }

    override val name: String
        get() = title

    companion object{
        const val title = "Прямоугольник"
        const val startX:Double = 0.0
        const val startY:Double = 0.0
        const val startW:Double = 0.0
        const val startH:Double = 0.0
    }
}

class RectCreator: ICreator {
    override fun create(): IFigure = FRect()
}