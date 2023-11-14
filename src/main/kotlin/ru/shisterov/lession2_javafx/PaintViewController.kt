package ru.shisterov.lession2_javafx

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import ru.shisterov.lession2_javafx.drawing.CalcType
import ru.shisterov.lession2_javafx.entities.IFigure
import ru.shisterov.lession2_javafx.model.FigureType

class PaintViewController {


    @FXML
    private lateinit var colorPicker: ColorPicker

    @FXML
    private lateinit var brushSize: TextField

    @FXML
    private lateinit var canvas: Pane

    @FXML
    private lateinit var shapes: HBox

    private fun getColor() = colorPicker.value
    private fun getWeightLine():Double {
        return try {
            brushSize.text.toDouble()
        } catch (e:Exception){
            println("Ошибка!! ${e.message}")
            ConfigApp.DEFAULT_WEIGHT
        }

    }

    //-------------
    private var figure: IFigure = ConfigApp.figureCreate(PaintButtonType.LINE)

    private var isDrawing = false

    private var currentType: PaintButtonType = PaintButtonType.LINE

    //===========

    //----------- Новые переменные
    ////---Разделяем по функциям
//    //Провайдер данных - предоставляет данные и сохраняет
//    val storageProvider = ConfigApp.getStorageProvider()
//    //Фабрика калькуляторов - предоставляет объект-калькулятор для расчета промежуточных точек
//    val calcFactory     = ConfigApp.getCalculatorFactory()
//    //Художник - создает объект Shape
//    val artist          = ConfigApp.makeArtist()

    //Менеджер рисования - выводить Shape на холст и сохраняет в хранилище
    private lateinit var managerDrawing:ManagerDrawing

    //============================


    //Нужная для javaFX функция
    fun initialize() {

        println("-------------- ИНИЦИАЛИЗАЦИЯ КОНТРОЛЛЕРА -------------")
        //Инициализируем
        managerDrawing = ConfigApp.makeManager(canvas, CalcType.CIRCLE)

        println("$managerDrawing")

        //Настраиваем кнопки
        //Берем список кнопок, создаем элементы, назначаем обработчик
        val buttons = ConfigApp.getButtons()
        buttons.forEach {

            val btn = Button(it.name).apply {
                setOnAction { a-> onButtonClick(it.type) }
            }
            shapes.children.add(btn)
        }

        //Настраиваем обработку мыши
        with(canvas) {
            setOnMouseMoved { onMouseMoved(it) }
            setOnMouseClicked { onMouseClick(it) }

//
        }

    }

    private fun onMouseClick(e: MouseEvent) {
        managerDrawing.onClick(e)

    }

    private fun onMouseMoved(e: MouseEvent) {
        managerDrawing.onMouseMoved(e)
    }


//    fun createButton(name:String): Button = Button(name)

    private fun onButtonClick(btnType:PaintButtonType){
        //при нажатии на кнопку элемента запоминаем текущий тип
        currentType = btnType
    }
    /*

    private fun onMouseMoved(e: MouseEvent) {
        //Перерисовываем фигуру, если в режиме рисования
        val x:Double = if (e.x > 0 ) e.x else 0.0
        val y:Double = if (e.y > 0 ) e.y else 0.0
        if (isDrawing)
            changeDrawing(x, y)
    }

    private fun changeDrawing(x: Double, y: Double) {
        if (isDrawing)
            figure.secondPoint(x, y)
    }

    //нажатие клавиши - смотрим статус
    private fun onMouseClick(e: MouseEvent) {
        println(" - Нажатие на клавишу мыши - (${e.x}, ${e.y})")

        if (isDrawing)
            finishDrawing()
        else
            startDrawing(e.x, e.y)
    }

    private fun finishDrawing() {
        println("!!! КОнец рисования фигуры ${figure.name}")
        //завершаем рисование объекта
        isDrawing = false
    }

    private fun startDrawing(x: Double, y: Double) {
        println("Начало рисования фигуры ${figure.name} - ($x, $y)")
        //если не в режиме рисования, переводим в режим рисования и создаем Shape-объект
        isDrawing = true
        figure = ConfigApp.figureCreate(currentType,x, y, getColor(), getWeightLine())
        canvas.children.add(figure.shape)
    }

     */

}