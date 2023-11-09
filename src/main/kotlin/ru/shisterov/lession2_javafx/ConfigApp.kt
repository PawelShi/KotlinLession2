package ru.shisterov.lession2_javafx

import javafx.scene.paint.Color
import ru.shisterov.lession2_javafx.creators.ICreator
import ru.shisterov.lession2_javafx.entities.*

//Здесь будем брать параметры объектов

//Типы кнопок
enum class PaintButtonType{
    LINE,
    CIRCLE,
    RECTANGLE,
}

//Класс параметры кнопок
data class PaintButtonInfo (
    val name:String,
    val type:PaintButtonType
)

object ConfigApp {



    //МАссив кнопок
    private val  buttonTypes = listOf<PaintButtonInfo>(
            PaintButtonInfo(FLine.title, PaintButtonType.LINE),
            PaintButtonInfo(FCircle.title, PaintButtonType.CIRCLE),
            PaintButtonInfo(FRect.title, PaintButtonType.RECTANGLE),
        )
    //массив создателей фигур - ТИп кнопки связан с объектом - создателем
    private val  figureCreators  = mapOf<PaintButtonType, ICreator>(
            PaintButtonType.LINE        to LineCreator(),
            PaintButtonType.CIRCLE      to CircleCreator(),
            PaintButtonType.RECTANGLE   to RectCreator(),
        )


//Метод для создания фигуры
    fun figureCreate(type:PaintButtonType,
                     x:Double = 0.0,
                     y:Double = 0.0,
                     color:Color = Color.BLACK,
                     width:Double = 2.0
    ):IFigure {
    //Находим создателя фигуры по типу кнопки
        val creator = figureCreators[type]

//    вызываем метод create у создателя
        return if (creator != null){
            creator.create()
        } else {
            LineCreator().create()
        }.apply { init(x, y, color, width) }
    }

    //метод возвращает список доступных кнопок
    fun getButtons(): List<PaintButtonInfo> = buttonTypes

}






