package ru.shisterov.lession2_javafx

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import ru.shisterov.lession2_javafx.data.IStorageProvider
import ru.shisterov.lession2_javafx.data.StorageProviderDefault
import ru.shisterov.lession2_javafx.calc.CalcType
import ru.shisterov.lession2_javafx.calc.CalculatorFactory
import ru.shisterov.lession2_javafx.drawing.Drawer
import ru.shisterov.lession2_javafx.drawing.MorphDrawer
import ru.shisterov.lession2_javafx.drawing.PaintDrawer
import ru.shisterov.lession2_javafx.manager.ManagerDrawing

//Здесь будем брать параметры объектов

//Типы кнопок


//Класс параметры кнопок
//data class PaintButtonInfo (
//    val name:String,
//    val type:CalcType
//)

const val FILE_EXT: String = "*.sav"
const val FILE_EXT_NAME: String = "Рисунок Kotlin lessions"
const val SAVE_TITLE_DIALOG = "Сохранить файл"
const val OPEN_TITLE_DIALOG = "Открыть файл"

object ConfigApp {

    //Константы
    const val SCREEN_WIDTH = 600.0
    const val SCREEN_HEIGHT = 480.0
    val DEFAULT_COLOR = Color.BLACK
    const val DEFAULT_WEIGHT = 2.0

    ///=========================================

    //Хранилище
    private val storage: IStorageProvider = StorageProviderDefault()
    fun getStorageProvider(): IStorageProvider = storage

    private val calcFactory = CalculatorFactory()

    fun makeDrawManager(drawer:Drawer, calcType: CalcType): ManagerDrawing {

        return ManagerDrawing(
            calculator = calcFactory.create(calcType),
            drawer = drawer
        )
    }

    fun makePaintDrawer(canvas: Pane): Drawer =
        PaintDrawer(
            storage = storage,
            canvas = canvas
        )
    fun makeMorphDrawer(canvas: Pane): Drawer =
        MorphDrawer(
            canvas = canvas
        )




}

enum class TypeFileDialog(val title:String){
    SAVE(SAVE_TITLE_DIALOG),
    OPEN(OPEN_TITLE_DIALOG)
}






