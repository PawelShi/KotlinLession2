package ru.shisterov.lession2_javafx

import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.data.IStorageProvider
import ru.shisterov.lession2_javafx.drawing.Artist
import ru.shisterov.lession2_javafx.drawing.FigureCalculator
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.MyPoint

class ManagerDrawing(
    val calculator: FigureCalculator,
    val artist: Artist,
    val canvas: Pane,
    val storage: IStorageProvider
) {

    data class DataDrawing(var shape: Shape?){
        var isDrawing: Boolean = false
            get() = (shape!= null)
        fun clear(){
            this.shape = null
                    }
    }

    private val data = DataDrawing(null)


//    private var isDrawing: Boolean = false

    // TODO: Вопрос с Shape по умолчанию, возможно в параметрах нужно задавать
//    private var shape: Shape = artist.default()
//        set(value) {
//            field = value
//            //- выводим shape на холст
//            canvas.childrenUnmodifiable.add(field)
//        }

    override fun toString(): String {
        return "Менеджер рисования(холст: $canvas, калькулятор: $calculator, художник: $artist, хранилище: $storage)"
    }

    fun onClick(e: MouseEvent) {
        val point = MyPoint(e.x, e.y)
        val isDrawing = data.isDrawing
        when {
            (e.button == MouseButton.PRIMARY && !isDrawing) -> {
                //Нажали на левую кнопку не в режиме рисования
                // !! Начинаем рисование
                println("* Старт рисования ${calculator.calcType}, точка: $point")

                //- Передаем калькулятору координаты
                val figure = calculator.start(point)
                //- Получаем от художника Shape
                data.shape = artist.draw(figure)
                //- выводим shape на холст
                canvas.children.add(data.shape)
            }

            (e.button == MouseButton.PRIMARY && isDrawing) -> {
                //Нажали на левую кнопку в режиме рисования
                println("  - Добавляем точку ${calculator.calcType} $point")
                //По сути просто проверяем завершено рисование или нет

                if (calculator.isFinishDrawing()) {
                    finishDrawing()
                } else {
                    calculator.setNewPoint(point)
                }

            }

            (e.button == MouseButton.SECONDARY && isDrawing) -> {
                //Нажали на правую кнопку в режиме рисования
                // !! отменяем рисование
            }

            (e.button == MouseButton.PRIMARY && !isDrawing) -> {
                //Нажали на левую кнопку не в режиме рисования
            }

        }
    }

    private fun finishDrawing() {
        println("* Завершение рисования ${calculator.calcType}")
        //Завершение рисования:
        // - сохраняем объект в БД
        storage.saveFigure(calculator.figure)
        //- очищаем data
        data.clear()
    }

    fun onMouseMoved(e: MouseEvent) {
        if (data.isDrawing){
            //Если в режиме рисования, то пересчитываем
            val point = MyPoint(e.x, e.y)
            // TODO: Изменяем последнюю точку

            val fig = calculator.changeLastPoint(point)
            artist.refresh(data.shape!!, fig)
        }

    }
}