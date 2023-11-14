package ru.shisterov.lession2_javafx.drawing

import javafx.scene.shape.Ellipse
import javafx.scene.shape.Line
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
    fun draw(figure:Figure):Shape{
        println("Рисуем $figure")
        return when(figure.type){
            FigureType.ELLIPSE -> createEllipse(figure as FigEllipse)
            FigureType.MULTILINE -> createMultyLine(figure as FigLine)

        }
    }

    private fun createMultyLine(figLine: FigLine):Shape {
        return Polyline().apply {
            points.addAll(figLine.points.flatMap { it.values })

        }
    }

    override fun toString(): String = "Универсальный"

    private fun createEllipse(ellipse: FigEllipse):Shape =
        with(ellipse) {
            Ellipse(center.x, center.y, radX, radY)
        }

    fun default(): Shape = Line()
    fun refresh(shape: Shape, figure: Figure) {
//        println(" -- refresh shape: $figure")
        when(figure.type){
            FigureType.ELLIPSE -> refreshEllipse(shape as Ellipse, figure as FigEllipse)
            FigureType.MULTILINE -> refreshMultiLine(shape as Polyline, figure as FigLine)

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

    private fun refreshMultiLine(polyline: Polyline, figLine: FigLine) {
        polyline.points.setAll(figLine.points.flatMap { it.values })
    }


}