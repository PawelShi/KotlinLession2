package ru.shisterov.lession2_javafx.drawing

import javafx.scene.shape.Ellipse
import javafx.scene.shape.Polygon
import javafx.scene.shape.Polyline
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.model.FigEllipse
import ru.shisterov.lession2_javafx.model.FigLine
import ru.shisterov.lession2_javafx.model.Figure
import ru.shisterov.lession2_javafx.model.FigureType

//Класс - Художник
//По объекту Figure создает объект Shape для вывода на холст
//Умеет рисовать 2 фигуры - эллипс и мультилинию
class Artist {
    fun draw(figure: Figure): Shape {
        println("Рисуем $figure")
        return when (figure.type) {
            FigureType.ELLIPSE -> createEllipse(figure as FigEllipse)
            FigureType.MULTILINE -> createMultyLine(figure as FigLine)

        }
    }

    private fun createMultyLine(figLine: FigLine): Shape {
        val coords = figLine.points.flatMap { it.values }
        return when (figLine.isClosed) {
            true -> Polygon().apply { points.addAll(coords) }
            else -> Polyline().apply { points.addAll(coords) }
        }
    }


    override fun toString(): String = "Универсальный"

    private fun createEllipse(ellipse: FigEllipse): Shape =
        with(ellipse) {
            Ellipse(center.x, center.y, radX, radY)
        }

    fun refresh(shape: Shape, figure: Figure) {
//        println(" -- refresh shape: $figure")
        when (figure.type) {
            FigureType.ELLIPSE -> refreshEllipse(shape as Ellipse, figure as FigEllipse)
            FigureType.MULTILINE -> refreshMultiLine(shape , figure as FigLine)

        }
    }

    private fun refreshEllipse(ellipse: Ellipse, figEllipse: FigEllipse) {
        with(ellipse) {
            with(figEllipse) {
                centerX = center.x
                centerY = center.y
                radiusX = radX
                radiusY = radY
            }
        }
    }

    private fun refreshMultiLine(shape: Shape, figLine: FigLine) {
        val coords = figLine.points.flatMap { it.values }
        when (shape) {
            is Polyline -> shape.points.setAll(coords)
            is Polygon  -> shape.points.setAll(coords)
        }
    }


}