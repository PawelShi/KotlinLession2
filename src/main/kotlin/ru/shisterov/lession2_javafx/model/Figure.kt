package ru.shisterov.lession2_javafx.model

enum class FigureType {
    ELLIPSE,
    MULTILINE,
}

//Базовый класс данных
//Содержит ID и Parameters
abstract class Figure(var id: Int?, val parameters: Map<String, Any> = mapOf()) {
    abstract val type: FigureType
}

data class MyPoint(val x: Double, val y: Double){
    val values: List<Double>
        get() = listOf(x, y)

    override fun toString(): String = "MyPoint($x, $y)"
}


class FigEllipse(
    id: Int?,
    val center: MyPoint,
    val radX: Double,
    val radY: Double,
) : Figure(id) {
    override val type = FigureType.ELLIPSE
//        get() = FigureType.ELLIPSE

    override fun toString(): String = "FigEllipse(type: $type, id:$id, center: $center, radiusX:$radX, radiusY:$radY)"
}

class FigLine(
    id: Int?,
    val points: MutableList<MyPoint> = mutableListOf(),
) : Figure(id) {
    override val type = FigureType.MULTILINE

    override fun toString(): String = "FigLine(type: $type, id:$id, points: $points)"
}