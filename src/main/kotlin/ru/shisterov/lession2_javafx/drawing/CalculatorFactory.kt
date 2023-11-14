package ru.shisterov.lession2_javafx.drawing

import javafx.geometry.Point2D
import javafx.scene.effect.Light.Point
import ru.shisterov.lession2_javafx.model.FigEllipse
import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint
import java.lang.Exception

enum class CalcType {
    LINE,
    CIRCLE,
}

//Интерфейс калькуляторов
interface FigureCalculator {
    //меняем последнюю точку
    fun changeLastPoint(lastPoint: MyPoint):Figure
    fun setNewPoint(newPoint: MyPoint):Figure
    fun start(myPoint: MyPoint): Figure
    fun isFinishDrawing(): Boolean
    val figure: Figure
    val calcType: CalcType

}

//Фабрика калькуляторов
//-- возвращает объект-калькулятор для указанного типа Figure
class CalculatorFactory {
    override fun toString(): String = "Фабрика вычислителей"
    fun create(calcType: CalcType): FigureCalculator =
        when (calcType) {
            CalcType.CIRCLE -> CircleCalculator()
            else -> LineCalculator()
        }

}

class LineCalculator : FigureCalculator {

    private val points = mutableListOf<MyPoint>()
    private val MAX_POINT = 2
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
        get() = FigLine(null, points)

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
}
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
        println("   -- changeLastPoint($lastPoint)")
        points[RADIUS_INDEX] = lastPoint
        println("   -- points^ $points")
        return figure
    }

    //больше нет точек
    override fun setNewPoint(newPoint: MyPoint): Figure = figure

    override fun start(myPoint: MyPoint): Figure {
        println("CIRCLE start $myPoint")
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