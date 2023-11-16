package ru.shisterov.lession2_javafx.manager

import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.drawing.Drawer
import ru.shisterov.lession2_javafx.drawing.FigureCalculator
import ru.shisterov.lession2_javafx.model.MyPoint

class ManagerDrawing(
    val calculator: FigureCalculator,
    val drawer: Drawer
) : IModeManager {

    private data class DataDrawing(var shape: Shape?) {
        val isDrawing: Boolean
            get() = (shape != null)

        fun clear() {
            this.shape = null
        }
    }

    private val data = DataDrawing(null)


    override fun toString(): String {
        return "Менеджер рисования( калькулятор: $calculator, рисователь: $drawer)"
    }

    override fun onClick(e: MouseEvent) {
        val point = MyPoint(e.x, e.y)
        val isDrawing = data.isDrawing
        when {
            (e.button == MouseButton.PRIMARY && !isDrawing) -> {
                //Нажали на левую кнопку не в режиме рисования
                // !! Начинаем рисование
                println("* Старт рисования ${calculator.calcType}, точка: $point")

                //- Передаем калькулятору координаты
                val figure = calculator.start(point)
                //помещаем фигуру
                data.shape = drawer.createShape(figure)
//                //- Получаем от художника Shape
//                data.shape = artist.draw(figure)
//                //- выводим shape на холст
//                canvas.children.add(data.shape)
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
                if (data.shape != null) drawer.deleteShape(data.shape!!)
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
//        storage.saveFigure(calculator.figure)
        drawer.addFigure(calculator.figure, data.shape)

        //- очищаем data
        data.clear()
    }

    override fun onMouseMoved(e: MouseEvent) {
        if (data.isDrawing) {
            //Если в режиме рисования, то пересчитываем
            val point = MyPoint(e.x, e.y)
            // TODO: Изменяем последнюю точку

            val fig = calculator.changeLastPoint(point)
            drawer.refresh(fig, data.shape!!)
        }

    }

    override fun close() {
        //Отмена действия, если есть
        //Удаляем элемент с холста
//        canvas.children.remove(data.shape)
        if (data.shape != null) {
            drawer.deleteShape(data.shape!!)
            data.clear()
        }

    }
}