package ru.shisterov.lession2_javafx

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import ru.shisterov.lession2_javafx.entities.IFigure

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
    private fun getWidhtLine() = brushSize.text.toDouble()

    //-------------
    var figure: IFigure = ConfigApp.figure

    var isDrawing = false

    var currentType: PaintButtonType = PaintButtonType.LINE

    //-------------


    fun initialize() {
        //Настраиваем кнопки

        //Берем список кнопок, создаем элементы, назначаем обработчик
        val buttons = ConfigApp.getButtons()
        buttons.forEach {

            val btn = createButton(it.name).apply {
                setOnAction { a-> onButtonClick(it.type) }
            }
            shapes.children.add(btn)
        }


        //Настраиваем обработку мыши
        canvas.setOnMouseMoved { onMouseMoved(it) }
        canvas.setOnMouseClicked { onMouseClick(it) }

    }
    fun createButton(name:String): Button = Button(name)

    fun onButtonClick(btnType:PaintButtonType){
        //при нажатии на кнопку элемента запоминаем текущий тип
        currentType = btnType

    }


    private fun onMouseMoved(e: MouseEvent) {
        //Перерисовываем фигуру, если в режиме рисования
        if (isDrawing)
            changeDrawing(e.x, e.y)
    }


    private fun changeDrawing(x: Double, y: Double) {
        if (isDrawing)
            figure.secondPoint(x, y)

    }

    //нажатие клавиши - смотрим статус
    private fun onMouseClick(e: MouseEvent) {
        if (isDrawing)
            finishDrawing(e.x, e.y)
        else
            startDrawing(e.x, e.y)
    }


    private fun finishDrawing(x: Double, y: Double) {
        println("!!! КОнец рисования !!!!")
        //завершаем рисование объекта
        isDrawing = false

    }

    private fun startDrawing(x: Double, y: Double) {
        println("Начало рисования !!!!")
        //если не в режиме рисования, переводим в режим рисования и создаем Shape-объект
        isDrawing = true
        figure = ConfigApp.figureCreator(currentType,x, y, getColor(), getWidhtLine())
        canvas.children.add(figure.shape)

    }

}