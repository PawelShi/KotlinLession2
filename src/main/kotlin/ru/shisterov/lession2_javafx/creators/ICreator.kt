package ru.shisterov.lession2_javafx.creators

import ru.shisterov.lession2_javafx.entities.IFigure

interface ICreator{
    fun create() : IFigure
}