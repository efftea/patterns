package Lists.File

import Lists.BD.StudentListInterface
import Lists.SuperStudentListClass
import datalist.DataListStudentShort
import model.Student
import view.Filter
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class StudentListTxt: SuperStudentListClass(), StudentListFileInterface {

    override fun readFromFile(path: String): MutableList<Student> {
        val file = File(path)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Некорректный адрес файла: $path")
        }
        try {
            return file.readLines().mapNotNull { line -> Student(line) }.toMutableList()
        } catch (e: FileNotFoundException) {
            throw IllegalArgumentException("Файл не найден: $path")
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка при чтении файла: ${e.message}")
        }
    }

    override fun writeToFile(students: MutableList<Student>, directory: String, fileName: String) {
        val file = File(directory, fileName)

        try {
            file.parentFile?.mkdirs()
            file.bufferedWriter().use { writer ->
                students.forEach { student ->
                    writer.write(student.toString())
                    writer.newLine()
                }
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка при записи в файл: ${e.message}")
        }
    }
}