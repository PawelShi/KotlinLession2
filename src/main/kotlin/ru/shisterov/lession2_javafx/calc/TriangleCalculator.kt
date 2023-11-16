package ru.shisterov.lession2_javafx.calc

import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint


// TODO: Повторяются как в LineCaclulator - унаследовать
class TriangleCalculator : ICalculator {

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
        get() = FigLine(null, true, points)

    override val calcType: CalcType = CalcType.TRIANGLE

    private companion object{
        const val MAX_POINTS = 3
    }
}