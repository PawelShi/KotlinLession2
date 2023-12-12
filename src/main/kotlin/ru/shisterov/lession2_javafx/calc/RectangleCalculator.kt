package ru.shisterov.lession2_javafx.calc

import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

class RectangleCalculator : ICalculator {
    private val points = mutableListOf<MyPoint>()

    override fun changeLastPoint(lastPoint: MyPoint): Figure {
        points[points.lastIndex] = lastPoint
        return figure
    }

    override fun setNewPoint(newPoint: MyPoint): Figure {
        points.add(newPoint)
        return figure
    }

    override fun start(myPoint: MyPoint): Figure {
        points.clear()
        //Сразу задаем 2 точки, вторую будем менять
        points.addAll(mutableListOf(myPoint, myPoint))
        return figure
    }

    override fun isFinishDrawing(): Boolean = points.size == MAX_POINTS

    override val figure: Figure
        get() {
            //Производим расчет промежуточных точек
            val p1 = points[0]
            val p2 = points[1]
            val recPoints = listOf<MyPoint>(p1,MyPoint(p2.x, p1.y), p2, MyPoint(p1.x, p2.y))
            return FigLine(null, true, recPoints)
        }

    override val calcType: CalcType = CalcType.RECTANGLE

    private companion object{
        const val MAX_POINTS = 2
    }

}
