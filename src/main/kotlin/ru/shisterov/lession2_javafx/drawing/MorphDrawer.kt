package ru.shisterov.lession2_javafx.drawing

import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.model.Figure

class MorphDrawer(val canvas: Pane) : Drawer {

    val artist = Artist()

    //Добавляем фигуру
    override fun addFigure(figure: Figure, existShape: Shape?) {

        val shape = existShape ?: artist.draw(figure)
        //Выводим на холст, еслои ранее не было
        if (existShape == null) canvas.children.add(shape)
    }

    //обновить существующее, только на холсте
    override fun refresh(fig: Figure, shape: Shape) {
        artist.refresh(shape, fig)
    }

    override fun deleteShape(shape: Shape) {
        canvas.children.remove(shape)
    }

    override fun createShape(figure: Figure): Shape {
        val shape = artist.draw(figure)
        canvas.children.add(shape)
        return shape
    }

    override fun clear() {
        canvas.children.clear()
    }
}