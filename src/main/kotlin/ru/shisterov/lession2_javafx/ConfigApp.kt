package ru.shisterov.lession2_javafx

import javafx.scene.paint.Color
import ru.shisterov.lession2_javafx.entities.FCircle
import ru.shisterov.lession2_javafx.entities.FLine
import ru.shisterov.lession2_javafx.entities.IFigure

//Здесь будем брать параметры объектов
object ConfigApp {

    var figure: IFigure = figureCreator(PaintButtonType.LINE,0.0,0.0, Color.BLACK,2.0)
        get() = field
        set(value) {field = value}


    fun figureCreator(type:PaintButtonType, x:Double, y:Double, color:Color, width:Double):IFigure {
        return when (type){
            PaintButtonType.LINE -> FLine()
            PaintButtonType.CIRCLE -> FCircle()
            else -> FLine()
        }.apply { init(x, y, color, width) }
    }

    fun getButtons(): List<PaintButtonInfo> {
        return listOf(
            PaintButtonInfo("Линия", PaintButtonType.LINE),
            PaintButtonInfo("Эллипс", PaintButtonType.CIRCLE),
        )
    }


}

enum class PaintButtonType{
    LINE,
    CIRCLE,
}

data class PaintButtonInfo (
    val name:String,
    val type:PaintButtonType
){

}