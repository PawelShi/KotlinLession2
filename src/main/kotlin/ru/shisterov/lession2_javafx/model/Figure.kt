package ru.shisterov.lession2_javafx.model

enum class FigureType {
    ELLIPSE,
    MULTILINE,
}

//Базовый класс данных
//Содержит ID и Parameters
interface MyJson{
    fun toMap():MutableMap<String, Any?>

}
abstract class Figure(
    var id: Int?,
    val isClosed: Boolean = false,
    // TODO: Реализовать
    val parameters: Map<String, Any> = mapOf()
) : MyJson{
    abstract val type: FigureType
    override fun toMap(): MutableMap<String, Any?> {
        println("-- toMap Figure")
        return mutableMapOf(
            "id" to id,
            "type" to type
        )
    }
}

data class MyPoint(val x: Double, val y: Double) : MyJson{
    val values: List<Double>
        get() = listOf(x, y)

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "x" to x,
            "y" to y,
        )
    }

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
    override fun toMap(): MutableMap<String, Any?> {
        return super.toMap().apply {
            putAll(
                setOf(
                    "center" to center.toMap(),
                    "radX" to radX,
                    "radY" to radY,
                )
            )
        }
    }

    override fun toString(): String = "FigEllipse(type: $type, id:$id, center: $center, radiusX:$radX, radiusY:$radY)"
}

class FigLine(
    id: Int?,
    isClosed: Boolean = false,
    val points: MutableList<MyPoint> = mutableListOf(),
) : Figure(id, isClosed) {
    override val type = FigureType.MULTILINE
    override fun toMap(): MutableMap<String, Any?> {
        return super.toMap().apply {
            putAll(
                setOf(
                    "isClosed" to isClosed,
                    "points" to points.map { p -> p.toMap() }
                )
            )
        }
    }

    override fun toString(): String = "FigLine(type: $type, id:$id, points: $points)"
}