package ru.shisterov.lession2_javafx.drawing

import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

enum class CalcType(val title:String) {
    LINE("Линия"),
    CIRCLE("Окружность"),
    TRIANGLE("Треугольник"),
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
            CalcType.TRIANGLE -> TriangleCalculator()
            else -> LineCalculator()
        }

}


