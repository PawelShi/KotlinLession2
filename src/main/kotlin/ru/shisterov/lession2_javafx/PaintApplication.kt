package ru.shisterov.lession2_javafx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class PaintApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PaintApplication::class.java.getResource("paint-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 600.0, 480.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

