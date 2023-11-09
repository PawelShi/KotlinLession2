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
            PaintButtonInfo(FLine.TITLE, PaintButtonType.LINE),
            PaintButtonInfo(FCircle.TITLE, PaintButtonType.CIRCLE),
            PaintButtonInfo(FRect.TITLE, PaintButtonType.RECTANGLE),
        )
    //массив создателей фигур - ТИп кнопки связан с объектом - создателем
    private val  figureCreators  = mapOf<PaintButtonType, ICreator>(
            PaintButtonType.LINE        to LineCreator(),
            PaintButtonType.CIRCLE      to CircleCreator(),
            PaintButtonType.RECTANGLE   to RectCreator(),
        )

    //Константы
    const val SCREEN_WIDTH = 600.0
    const val SCREEN_HEIGHT = 480.0
    val DEFAULT_COLOR = Color.BLACK
    const val DEFAULT_WEIGHT = 2.0

    //Метод для создания фигуры
    fun figureCreate(type:PaintButtonType,
                     x:Double = 0.0,
                     y:Double = 0.0,
                     color:Color = DEFAULT_COLOR,
                     width:Double = DEFAULT_WEIGHT,
    ):IFigure {
    //Находим создателя фигуры по типу кнопки
        val creator = figureCreators[type]
//    вызываем метод create у создателя
        return (creator?.create() ?: LineCreator().create()).apply { init(x, y, color, width) }
    }

    //метод возвращает список доступных кнопок
    fun getButtons(): List<PaintButtonInfo> = buttonTypes

}






