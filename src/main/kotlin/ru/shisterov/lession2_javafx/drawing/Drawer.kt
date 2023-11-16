package ru.shisterov.lession2_javafx.drawing

import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.data.IStorageProvider
import ru.shisterov.lession2_javafx.model.Figure

class Drawer (val storage:IStorageProvider, val canvas: Pane){

    val artist = Artist()


    //Добавляем фигуру
    fun addFigure(figure: Figure, existShape: Shape? = null) {

        val shape = existShape ?: artist.draw(figure)
        //Выводим на холст, еслои ранее не было
        if  (existShape == null) canvas.children.add(shape)
        //заносим в БД
        storage.saveFigure(figure)

    }

    //обновить существующее, только на холсте
    fun refresh(fig: Figure, shape: Shape) {
        artist.refresh(shape, fig)
    }

    fun deleteShape(shape: Shape) {

        canvas.children.remove(shape)
    }

    fun createShape(figure: Figure): Shape {
        val shape = artist.draw(figure)
        canvas.children.add(shape)
        return  shape
    }

    fun clear() {
        canvas.children.clear()
    }
}