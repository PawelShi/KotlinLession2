package ru.shisterov.lession2_javafx

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import ru.shisterov.lession2_javafx.data.IStorageProvider
import ru.shisterov.lession2_javafx.data.StorageProviderDefault
import ru.shisterov.lession2_javafx.calc.CalcType
import ru.shisterov.lession2_javafx.calc.CalculatorFactory
import ru.shisterov.lession2_javafx.drawing.Drawer
import ru.shisterov.lession2_javafx.manager.ManagerDrawing
import java.io.File

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

    fun makeDrawer(canvas: Pane): Drawer =
        Drawer(
            storage = storage,
            canvas = canvas
        )

    fun showSaveOpenFileDialog(
        initFileName: String = "",
        dialog: TypeFileDialog
    ): File? {
        val chooser = FileChooser().apply {
            this.title = dialog.title
            initialFileName = initFileName
            extensionFilters.addAll(
                FileChooser.ExtensionFilter(FILE_EXT_NAME, FILE_EXT)
            )
        }
        return with(chooser){
            if (dialog == TypeFileDialog.SAVE) showSaveDialog(null) else showOpenDialog(null)
        }
    }

    fun loadData(fileName: String = ""): String {
        // Просто чтение текстовых данных из файла по полному пути.
        println("  -- namefile = $fileName")
        val file = File(fileName)
        if (file.exists()){
            return file.readText()
        }  else {
            throw Exception("Not exist file $fileName!!")
        }
    }

    fun saveData(file: File, data: String) {
        // Просто сохранение текстовых данных в файл.
        file.writeText(data)

    }


}

enum class TypeFileDialog(val title:String){
    SAVE(SAVE_TITLE_DIALOG),
    OPEN(OPEN_TITLE_DIALOG)
}






