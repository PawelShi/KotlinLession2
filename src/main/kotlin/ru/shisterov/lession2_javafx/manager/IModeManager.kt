package ru.shisterov.lession2_javafx.manager

import javafx.scene.input.MouseEvent
import javafx.scene.shape.Shape
import ru.shisterov.lession2_javafx.drawing.Drawer

interface IModeManager {
    fun onClick(e: MouseEvent)
    fun onMouseMoved(e: MouseEvent)
    fun close()
    fun setOnFinish(callback: FinishFunc)
    fun getCurrentDrawer(): Drawer
}

typealias FinishFunc = (Shape?) -> Unit