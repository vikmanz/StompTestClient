package com.vikmanz.stomptc.utils

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import java.io.File

object FileDialogUtil {

    private val defaultDir: File
        get() = File(System.getProperty("user.dir"))

    fun showSaveDialog(defaultFileName: String = "stomp_config.json"): File? {
        val fileChooser = JFileChooser(defaultDir).apply {
            dialogTitle = "Save Configuration"
            selectedFile = File(defaultDir, defaultFileName)
            fileFilter = FileNameExtensionFilter("JSON files", "json")
        }
        val result = fileChooser.showSaveDialog(null)
        return if (result == JFileChooser.APPROVE_OPTION) fileChooser.selectedFile else null
    }

    fun showLoadDialog(): File? {
        val fileChooser = JFileChooser(defaultDir).apply {
            dialogTitle = "Load Configuration"
            fileFilter = FileNameExtensionFilter("JSON files", "json")
        }
        val result = fileChooser.showOpenDialog(null)
        return if (result == JFileChooser.APPROVE_OPTION) fileChooser.selectedFile else null
    }

}