package ru.shisterov.lession2_javafx

import javafx.scene.control.Button
import javafx.scene.paint.Color
import ru.shisterov.lession2_javafx.entities.FCircle
import ru.shisterov.lession2_javafx.entities.IFigure
import ru.shisterov.lession2_javafx.entities.FLine

//Здесь будем брать параметры объектов
object ConfigApp {

    var figure: IFigure = figureCreator(0.0,0.0, Color.BLACK,2.0)
        get() = field
        set(value) {field = value}


    fun figureCreator(x:Double, y:Double, color:Color, width:Double):IFigure = FCircle().apply {
        init(x, y, color, width)
    }

    fun getButtons(): List<Button> {
        return listOf(
            Button("Линия"),
            Button("Эллипс"),
        )
    }


}