package ru.shisterov.lession2_javafx.drawing

import javafx.geometry.Point2D
import ru.shisterov.lession2_javafx.model.FigEllipse
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

class CircleCalculator : FigureCalculator{

    private val points = mutableListOf(MyPoint(0.0,0.0), MyPoint(0.0,0.0))
    companion object {
        private const val MAX_POINT = 2
        private const val CENTER_INDEX = 0
        private const val RADIUS_INDEX = 1
    }
    private fun getCenter(): MyPoint = points[CENTER_INDEX]
    private fun getRadius():Double {
        val pc = Point2D(getCenter().x, getCenter().y)
        val pr = Point2D(points[RADIUS_INDEX].x,points[RADIUS_INDEX].y )
        return pc.distance(pr)
    }

    override fun changeLastPoint(lastPoint: MyPoint): Figure {
//        println("   -- changeLastPoint($lastPoint)")
        points[RADIUS_INDEX] = lastPoint
//        println("   -- points^ $points")
        return figure
    }

    //больше нет точек
    override fun setNewPoint(newPoint: MyPoint): Figure = figure

    override fun start(myPoint: MyPoint): Figure {
//        println("CIRCLE start $myPoint")
        points[CENTER_INDEX] = myPoint
        points[RADIUS_INDEX] = myPoint

        return figure
    }

    override fun isFinishDrawing(): Boolean = points.size == MAX_POINT

    override val figure: Figure
        get() = FigEllipse(null, getCenter(), getRadius(), getRadius())
    override val calcType: CalcType = CalcType.CIRCLE

    override fun toString(): String = "Вычислитель окружности"


}