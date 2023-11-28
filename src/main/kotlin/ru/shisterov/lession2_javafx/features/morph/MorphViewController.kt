package ru.shisterov.lession2_javafx.features.morph

import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.geometry.Orientation
import javafx.geometry.Point2D
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import javafx.scene.text.Text
import ru.shisterov.lession2_javafx.ConfigApp
import ru.shisterov.lession2_javafx.calc.CalcType
import ru.shisterov.lession2_javafx.manager.IModeManager
import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.MyPoint
import java.awt.Color
import kotlin.math.round

//enum class MorphViewMode(val message: String) {
//    MORPH("Преобразование"),
//    DRAW("Рисование")
//}
enum class FigureMode(val title: String) {
    ONE("Фигура 1"),
    TWO("Фигура 2")
}

class MorphViewController {

    @FXML
    private var canvas: Pane = Pane()

    @FXML
    private var figureToolbar: ToolBar = ToolBar()

    @FXML
    private var morphToolbar: ToolBar = ToolBar()

    private var morphSlider: Slider = Slider().apply {
        min = 0.0
        max = 100.0
        prefWidth = 200.0
        isVisible = false
    }

    private var morphValue: TextField = TextField().apply {
        isEditable = false
        isVisible = false
    }


    private var morphShape:Shape? = null

    //Слушаем изменения слайдера преобразований
    private val valueListener = ChangeListener<Number> { observable, oldValue, newValue ->
        val absValue = round(newValue.toDouble())
        morphSlider.value = absValue
        morphValue.text = absValue.toString()
        //Рассчитываем точки фигуры и выводим ее
        val points = morphCalculator.calcMorphPoints(
            first = figuresPoints[FigureMode.ONE],
            second = figuresPoints[FigureMode.TWO],
            percent = absValue,
            startPoint = Point2D(canvas.width / 2.0, canvas.height / 2.0)
        )
        //Создаем фигуру и выводим
        val figure = FigLine(null, true, points.map { MyPoint(it.x, it.y) })
        //Выводим фигуру
        if (morphShape != null){
            modeManager?.getCurrentDrawer()?.refresh(figure, morphShape!!)
        } else {
            morphShape = modeManager?.getCurrentDrawer()?.createShape(figure)
        }

    }


    private val buttons = CalcType.values().map { calcType ->
        Button(calcType.title).apply {
            setOnAction { _ -> onFigureButtonClick(calcType) }
        }
    }

    private var figureMode: FigureMode = FigureMode.ONE

    private var modeManager: IModeManager? = null
        set(value) {
            modeManager?.close()
            field = value
        }

    private fun createModeManager(calcType: CalcType = CalcType.LINE): IModeManager =
        ConfigApp.makeDrawManager(
            drawer = ConfigApp.makeMorphDrawer(canvas),
            calcType = calcType
        ).apply {
            setOnFinish { it?.let { setFigure(it) } }
        }

    private val figures: MutableMap<FigureMode, Shape?> = mutableMapOf(
        FigureMode.ONE to null,
        FigureMode.TWO to null,
    )

    private val figuresPoints: MutableMap<FigureMode, List<Point2D>> = mutableMapOf(
        FigureMode.ONE to mutableListOf<Point2D>(),
        FigureMode.TWO to mutableListOf<Point2D>(),
    )

    private val morphCalculator = MorphCalculator()

    //задаем фигуры для преобразования
    private fun setFigure(shape: Shape) {
        //Запоминаем фигуру
        val oldFigure = figures[figureMode];
        if (oldFigure != null) canvas.children.remove(oldFigure)
        figures[figureMode] = shape
        //Передаем на расчет
        figuresPoints[figureMode] = morphCalculator.getPoints(DEFAULT_POINTS, shape)
        val isVisible = (figures[FigureMode.ONE] != null && figures[FigureMode.TWO] != null)
        morphSlider.isVisible = isVisible
        morphValue.isVisible = isVisible
    }

    fun initialize() {
        val groupRadioButton = ToggleGroup()

        with(figureToolbar.items) {
            FigureMode.values().forEach { mode ->
                val radioButton = RadioButton(mode.title).apply {
                    toggleGroup = groupRadioButton
                    setOnAction { onFigureToggle(mode) }
                    isSelected = (mode == figureMode)
                }
                add(radioButton)
            }
            add(Separator(Orientation.HORIZONTAL))
            addAll(buttons)
        }
        //

        morphSlider.valueProperty().addListener(valueListener)

        with(morphToolbar.items) {
            add(Text("Панель преобразования"))
            add(Separator(Orientation.HORIZONTAL))
            add(morphSlider)
            add(Separator(Orientation.HORIZONTAL))
            add(morphValue)
        }

        //Настраиваем обработку мыши
        with(canvas) {
            setOnMouseMoved { onMouseMoved(it) }
            setOnMouseClicked { onMouseClick(it) }
        }
    }

    private fun onFigureToggle(mode: FigureMode) {
        figureMode = mode
    }

    private fun onFigureButtonClick(calcType: CalcType) {
        //Нажатие на кнопку рисования
        modeManager = createModeManager(calcType)
    }

    private fun onMouseClick(e: MouseEvent) {
        modeManager?.onClick(e)
    }

    private fun onMouseMoved(e: MouseEvent) {
        modeManager?.let { it.onMouseMoved(e) }
    }

    private companion object {

        val FIRST_SHAPE_COLOR = Color.BLUE
        val SECOND_SHAPE_COLOR = Color.GREEN
        val MORPH_SHAPE_COLOR = Color.RED
        const val MORPH_WEIGHT = 3.0
        const val DEFAULT_POINTS = 20
    }

}

