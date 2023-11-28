package ru.shisterov.lession2_javafx.drawing

import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.data.IStorageProvider
import ru.shisterov.lession2_javafx.model.Figure


/*Единый объект для работы холстом:
- при добавлении фигуры создает объект на холсте и запоминает в БД фигуру

 */
class PaintDrawer (val storage:IStorageProvider, val canvas: Pane):Drawer{

    val artist = Artist()

    //Добавляем фигуру
    override fun addFigure(figure: Figure, existShape: Shape?) {

        val shape = existShape ?: artist.draw(figure)
        //Выводим на холст, если ранее не было
        if  (existShape == null) canvas.children.add(shape)
        //заносим в БД
        storage.saveFigure(figure)

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
        return  shape
    }

    override fun clear() {
        storage.clear()
        canvas.children.clear()
    }
}