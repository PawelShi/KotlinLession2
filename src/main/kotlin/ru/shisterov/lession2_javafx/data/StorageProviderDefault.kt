package ru.shisterov.lession2_javafx.data

import ru.shisterov.lession2_javafx.model.Figure

class StorageProviderDefault :IStorageProvider {

    val dataStorage :MutableList<Figure> = mutableListOf()
    override fun loadData(): List<Figure> = dataStorage

    override fun saveFigure(figure: Figure) {
        dataStorage.add(figure)
    }

    override fun toString(): String = "Память"
}