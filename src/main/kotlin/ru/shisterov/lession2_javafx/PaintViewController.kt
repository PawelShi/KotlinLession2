package ru.shisterov.lession2_javafx

import com.google.gson.Gson
import com.google.gson.JsonObject
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import ru.shisterov.lession2_javafx.calc.CalcType
import ru.shisterov.lession2_javafx.drawing.Drawer
import ru.shisterov.lession2_javafx.manager.IModeManager
import ru.shisterov.lession2_javafx.model.FigEllipse
import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.FigureType
import java.io.File


enum class PaintMode(val message: String) {
    MOVE("Перемещение"),
    DRAW("Рисование")
}

class PaintViewController {


    @FXML
    private lateinit var colorPicker: ColorPicker

    @FXML
    private lateinit var brushSize: TextField

    @FXML
    private lateinit var canvas: Pane

    @FXML
    private lateinit var buttons: HBox

    private fun getColor() = colorPicker.value
    private fun getWeightLine(): Double {
        return try {
            brushSize.text.toDouble()
        } catch (e: Exception) {
            println("Ошибка!! ${e.message}")
            ConfigApp.DEFAULT_WEIGHT
        }

    }

    //============================


    private var paintMode: PaintMode = DEFAULT_MODE
        set(value) {
            field = value
            modeManager = createModeManager()
        }

    //Менеджер режима работы
    private var modeManager: IModeManager? = null
        set(value) {
            modeManager?.let { it.close() }
            field = value
        }

    private fun createModeManager(calcType: CalcType = CalcType.LINE): IModeManager =
        when (paintMode) {
            PaintMode.DRAW -> ConfigApp.makeDrawManager(drawer, calcType)
            // TODO: Тут будет специальный объект для управления перемещением
            PaintMode.MOVE -> ConfigApp.makeDrawManager(drawer, calcType)
        }

    private lateinit var drawer: Drawer

    //Нужная для javaFX функция
    fun initialize() {

        println("-------------- ИНИЦИАЛИЗАЦИЯ КОНТРОЛЛЕРА -------------")
        //создаем Рисователя
        drawer = ConfigApp.makeDrawer(canvas)

        // TODO: Как-то некрасиво, рассмотреть возможность убрать canvas из параметров
        modeManager = createModeManager()
        println("$modeManager")

        //Настраиваем кнопки
        CalcType.values().forEach {

            val btn = Button(it.title).apply {
                setOnAction { _ -> onFigureButtonClick(it) }
            }
            buttons.children.add(btn)
        }


        //Настраиваем обработку мыши
        with(canvas) {
            setOnMouseMoved { onMouseMoved(it) }
            setOnMouseClicked { onMouseClick(it) }
        }

    }

    private fun onMouseClick(e: MouseEvent) {
        modeManager?.onClick(e)

    }

    private fun onMouseMoved(e: MouseEvent) {
        modeManager?.let { it.onMouseMoved(e) }
    }

    private fun onFigureButtonClick(calcType: CalcType) {
        //Нажатие на кнопку рисования
        paintMode = PaintMode.DRAW
        modeManager = createModeManager(calcType)
    }

    fun onSaveClick() {
        println("  --- SAVE DATA ---")
        val file: File? = ConfigApp.showSaveOpenFileDialog("newFile", TypeFileDialog.SAVE)
        if (file != null) {
            val storage = ConfigApp.getStorageProvider()
            val listFigures = storage.loadData()
            val json = Gson().toJson(
                listFigures.map { figure ->
                    figure.toJson()
                })
            println("--- JSON -- ")
            println("$json")
            ConfigApp.saveData(file, json)
            println("  --- finish")
        } else {
            println("  --- not set name file")
        }

    }

    fun onLoadClick() {
        println("  --- LOAD DATA ---")
        val file: File? = ConfigApp.showSaveOpenFileDialog("", TypeFileDialog.OPEN)
        if (file != null) {
            println("--- file=$file")
            try {
                val json = ConfigApp.loadData(file.absolutePath)
                println("  --- JSON = $json")

                val data = Gson().fromJson(json, Array<JsonObject>::class.java)
                val listFigures = data.map { obj ->
                    val typeString = obj.get("type").asString

                    if (typeString == FigureType.ELLIPSE.name) {
                        Gson().fromJson(obj, FigEllipse::class.java)
                    } else {
                        Gson().fromJson(obj, FigLine::class.java)
                    }
                }

                //Очищаем холст
                drawer.clear()
                //Выводим все эдементы
                listFigures.forEach { figure -> drawer.addFigure(figure) }

                println("  --- finish")
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
            }

        } else {
            println("  --- no name file")
        }

    }


    private companion object {
        private val DEFAULT_MODE = PaintMode.MOVE

    }

}