package ru.shisterov.lession2_javafx.calc

import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

class LineCalculator : ICalculator {

    private val points = mutableListOf<MyPoint>()

    override fun changeLastPoint(lastPoint: MyPoint): Figure {
        points[points.lastIndex] = lastPoint
        return figure
    }

    //Задаем новую точку
    override fun setNewPoint(newPoint: MyPoint): Figure {
        points.add(newPoint)
        return figure
    }

    override fun isFinishDrawing(): Boolean = points.size == MAX_POINT

    override val figure: Figure
        get() = FigLine(null, false, points)

    //Запускаем расчет
    override fun start(myPoint: MyPoint): Figure {
        points.clear()
        //Сразу задаем 2 точки, вторую будем менять
        points.addAll(mutableListOf(myPoint, myPoint))

        //Новую фигуру создаем
        return figure
    }

    override fun toString(): String = "Линейный вычислитель"
    override val calcType: CalcType = CalcType.LINE

    private companion object {
        const val MAX_POINT = 2
    }
}