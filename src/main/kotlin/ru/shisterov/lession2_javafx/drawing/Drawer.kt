package ru.shisterov.lession2_javafx.drawing

import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.model.Figure

interface Drawer {
    fun addFigure(figure: Figure, existShape: Shape? = null)
    fun refresh(fig: Figure, shape: Shape)
    fun deleteShape(shape: Shape)
    fun createShape(figure: Figure): Shape
    fun clear()
}