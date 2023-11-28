package ru.shisterov.lession2_javafx.features.morph

import javafx.collections.ObservableList
import javafx.geometry.Point2D
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Polygon
import javafx.scene.shape.Polyline
import javafx.scene.shape.Shape
import kotlin.math.*

class MorphCalculator {

    data class Otrezok(val p1: Point2D, val p2: Point2D) {
        val length: Double
            get() = p1.distance(p2)
    }

    fun getPoints(countPoints: Int, shape: Shape): List<Point2D> =
        when (shape) {
            is Ellipse -> getEllipsePoints(countPoints, shape)
            is Polygon -> getPolygonPoints(countPoints, shape.points, true)
            is Polyline -> getPolygonPoints(countPoints, shape.points)
            else -> listOf()
        }



    private fun getPolygonPoints(countPoints: Int, coords: ObservableList<Double>, isPolygon: Boolean = false): List<Point2D> {


        val points = convertToListPoints(coords)

        val lines: List<Otrezok> = explodeToLine(points , isPolygon)

        val delta: Double = when(isPolygon){
            true -> calcLengthFigure(lines) / (countPoints)
            false -> calcLengthFigure(lines) / (countPoints - 1 )
        }

        //Считаем промежуточные точки
//        val morphPoints = mutableListOf<Point2D>(lines[0].p1)
        val morphPoints = mutableListOf<Point2D>()
        println(" ---==== Расчет точек фигуры  ====---")
        println("  -- Точек - ${points.size}")
        println("  -- Отрезков - ${lines.size}")
        println("  -- Длина фигуры = ${calcLengthFigure(lines)}")
        println("  -- Среднее растояние между точками = $delta")
        //пробегаем по отрезку и считаем промежуточные точки
        lines.forEach { otrezok ->
            //Число точек на отрезке
            var countInternalPoints: Int = (otrezok.length / delta).roundToInt() - 1
            if (countInternalPoints < 0) {
                countInternalPoints = 1
            }

            //внутренние интервалы между точками
            val internalDelta = otrezok.length / (countInternalPoints + 1)

            println("  --- Отрезок: длина= ${otrezok.length}, точек внутри = $countInternalPoints, дельта=$internalDelta")
            //Добавляем первую точку отрезка
            morphPoints.add(otrezok.p1)
            println("  ---- Первая точка: ${otrezok.p1}")
            //рассчитываем промежуточные точки
            for (i in 1..countInternalPoints) {
                println("  ---- ${i+1} точка: ${calcPoint(otrezok, internalDelta * i)}")
                morphPoints.add(calcPoint(otrezok, internalDelta * i))
            }
        }
        println("  ---- После расчета промежуточных точек: ${morphPoints.size}")
        //если не полигон, то последняя точка не первая
        if (!isPolygon) {
            morphPoints.add(lines.last().p2)
        }

        when {
            (morphPoints.size > countPoints) -> {
                val countDel = morphPoints.size - countPoints
                for (i in 1..countDel){
                    val idx:Int = (countPoints/2.0).roundToInt()
                    morphPoints.removeAt(idx + i)
                }
            }

        }
        println(" -- ВСЕГО точек: ${morphPoints.size}")
        println(" ---=====================================----")
        return morphPoints
    }


    private fun calcPoint(line: Otrezok, distance: Double): Point2D {
        val length = line.length
        val dirX = ((line.p2.x - line.p1.x) / length) * distance
        val dirY = ((line.p2.y - line.p1.y) / length) * distance
        return Point2D(dirX + line.p1.x, dirY + line.p1.y)

    }

    //Разбиваем на отрезки
    private fun explodeToLine(points: List<Point2D>, isPolygon: Boolean): List<Otrezok> {
        val pOne = points[0]
        val list = mutableListOf<Otrezok>()
        for (i in points.indices) {
            val p1: Point2D = points[i]
            val p2: Point2D = when {
                (i == points.lastIndex && isPolygon) -> pOne
                (i == points.lastIndex && !isPolygon) -> p1
                else -> points[i + 1]
            }
            if (p1 != p2) list.add(Otrezok(p1, p2))
        }
        return list
    }

    private fun calcLengthFigure(lines: List<Otrezok>): Double =
        lines
            .map { it.length }
            .sum()

    private fun convertToListPoints(points: ObservableList<Double>): List<Point2D> {
        val listPoints = mutableListOf<Point2D>()
        for (i in points.indices step 2) {
            listPoints.add(Point2D(points[i], points[i + 1]))
        }
        return listPoints
    }

    private fun getEllipsePoints(countPoints: Int, shape: Ellipse): List<Point2D> {

        val delta = (2 * PI) / countPoints
        val radX = shape.radiusX
        val radY = shape.radiusY
        val cen = Point2D(shape.centerX, shape.centerY)

        val res = mutableListOf<Point2D>()
        for (i in 0 until countPoints) {
            val ang = i * delta
            val point = calcEllipsePoint(cen, radX, radY, ang)
            res.add(point)
        }
        return res
    }

    private fun calcEllipsePoint(cen: Point2D, radX: Double, radY: Double, ang: Double): Point2D =
        Point2D(cen.x + radX * cos(ang), cen.y + radY * sin(ang))

    fun calcMorphPoints(first: List<Point2D>?, second: List<Point2D>?, percent: Double, startPoint: Point2D): List<Point2D> {

        if (first == null || second == null)
            throw Exception("Не должно быть пустого списка точек. Фигура1($first) / Фигура2($second)")

        //Расчет точек фигуры
        if (first.size != second.size)
            throw Exception("Не совпадают длины списков точек фигур - Фигура 1(длина = ${first.size}) / Фигура 2(длина = ${second.size})")

        val morph = mutableListOf<Point2D>()
        var sumX = 0.0
        var sumY= 0.0
        for (i in 0 until first.size){
            val length = first[i].distance(second[i])
            val morphDist = length * percent / 100.0
            val morphPoint = calcPoint(Otrezok(first[i], second[i]), morphDist)
            morph.add(morphPoint)
            sumX += morphPoint.x
            sumY += morphPoint.y
        }
        val centerX = sumX / first.size
        val centerY = sumY / first.size
        val deltaX = centerX - startPoint.x
        val deltaY = centerY - startPoint.y
        return morph.map { point -> Point2D(point.x - deltaX, point.y - deltaY) }

    }


}