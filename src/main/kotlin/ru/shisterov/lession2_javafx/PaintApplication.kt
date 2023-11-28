package ru.shisterov.lession2_javafx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class PaintApplication : Application() {
    override fun start(stage: Stage) {
//        startPaintWindow(stage)
        startMorphWindow(stage)
        stage.show()
    }

    fun startPaintWindow(stage: Stage){
        val fxmlLoader = FXMLLoader(PaintApplication::class.java.getResource("paint-view.fxml"))
        val scene = Scene(fxmlLoader.load(), ConfigApp.SCREEN_WIDTH, ConfigApp.SCREEN_HEIGHT)

        with(stage) {
            title = "Kotlin Lessions. Рисование"
            stage.scene = scene
            show()
        }
    }

    fun startMorphWindow(stage: Stage){
        val fxmlLoader = FXMLLoader(PaintApplication::class.java.getResource("morph-view.fxml"))
        val scene = Scene(fxmlLoader.load(), ConfigApp.SCREEN_WIDTH, ConfigApp.SCREEN_HEIGHT)

        with(stage) {
            title = "Kotlin Lessions. Преобразование"
            stage.scene = scene
            show()
        }

    }

}

