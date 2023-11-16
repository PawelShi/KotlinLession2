package ru.shisterov.lession2_javafx

import javafx.stage.FileChooser
import java.io.File

fun showFileDialog(
    initFileName: String = "",
    dialog: TypeFileDialog
): File? {
    val chooser = FileChooser().apply {
        this.title = dialog.title
        initialFileName = initFileName
        extensionFilters.addAll(
            FileChooser.ExtensionFilter(FILE_EXT_NAME, FILE_EXT)
        )
    }
    return with(chooser){
        if (dialog == TypeFileDialog.SAVE) showSaveDialog(null) else showOpenDialog(null)
    }
}

fun loadDataFromFile(fileName: String = ""): String {
    // Просто чтение текстовых данных из файла по полному пути.
    println("  -- namefile = $fileName")
    val file = File(fileName)
    if (file.exists()){
        return file.readText()
    }  else {
        throw Exception("Not exist file $fileName!!")
    }
}

fun saveDataToFile(file: File, data: String) {
    // Просто сохранение текстовых данных в файл.
    file.writeText(data)

}