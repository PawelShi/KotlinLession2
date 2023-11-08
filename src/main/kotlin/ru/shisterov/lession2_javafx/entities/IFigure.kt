package ru.shisterov.lession2_javafx.entities

import javafx.scene.paint.Color
import javafx.scene.shape.Shape

interface IFigure {

    val shape:Shape
    fun init(x: Double, y: Double, color: Color, width: Double)
    fun firstPoint(x: Double, y: Double)
    fun secondPoint(x: Double, y: Double)
    val name: String


}