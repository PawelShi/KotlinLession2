package ru.shisterov.lession2_javafx.manager

import javafx.scene.input.MouseEvent

interface IModeManager {
    fun onClick(e: MouseEvent)
    fun onMouseMoved(e: MouseEvent)
    fun close()

}