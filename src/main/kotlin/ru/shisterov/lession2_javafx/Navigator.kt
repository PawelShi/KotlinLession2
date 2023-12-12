package ru.shisterov.lession2_javafx

import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage


enum class NavigatorRoute(val view: String, val title: String) {
    PAINT("paint-view.fxml", "Kotlin Lessions. Рисование"),
    MORPH("morph-view.fxml", "Kotlin Lessions. Преобразование"),
}

object Navigator {

    fun showWindow(stage: Stage = Stage(), route: NavigatorRoute, windowMode: Modality? = null) {

        val fxmlLoader = FXMLLoader(PaintApplication::class.java.getResource(route.view))
        val winScene = Scene(fxmlLoader.load(), ConfigApp.SCREEN_WIDTH, ConfigApp.SCREEN_HEIGHT)

        println("winMode = $windowMode")
        with(stage) {
            title = route.title
            scene = winScene
            if (windowMode != null) {
                windowMode?.let {
                    initModality(it)
                    showAndWait()
                }
            } else {
                show()
            }

        }

    }

}