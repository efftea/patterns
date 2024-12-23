package Lists.File

import model.Student

interface StudentListFileInterface {

    fun readFromFile(filePath: String): MutableList<Student>

    fun writeToFile(students: MutableList<Student>, directory: String, fileName: String)
}