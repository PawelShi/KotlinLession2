package ru.shisterov.lession2_javafx.calc

import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

//Интерфейс калькуляторов
interface ICalculator {
    //меняем последнюю точку
    fun changeLastPoint(lastPoint: MyPoint): Figure
    fun setNewPoint(newPoint: MyPoint): Figure
    fun start(myPoint: MyPoint): Figure
    fun isFinishDrawing(): Boolean
    val figure: Figure
    val calcType: CalcType

}