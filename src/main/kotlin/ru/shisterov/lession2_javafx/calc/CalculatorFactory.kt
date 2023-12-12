package ru.shisterov.lession2_javafx.calc

import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

enum class CalcType(val title:String) {
    LINE("Линия"),
    CIRCLE("Окружность"),
    TRIANGLE("Треугольник"),
    // TODO: можно добавить остальные
    RECTANGLE("Прям.")
}

//Фабрика калькуляторов
//-- возвращает объект-калькулятор для указанного типа Figure
class CalculatorFactory {
    override fun toString(): String = "Фабрика вычислителей"
    fun create(calcType: CalcType): ICalculator =
        when (calcType) {
            CalcType.CIRCLE -> CircleCalculator()
            CalcType.TRIANGLE -> TriangleCalculator()
            CalcType.RECTANGLE -> RectangleCalculator()
            else -> LineCalculator()
        }

}


