package ru.shisterov.lession2_javafx.data

import ru.shisterov.lession2_javafx.model.Figure

interface IStorageProvider {
    fun loadData():List<Figure>
    fun saveFigure(figure:Figure)
    fun clear()
}