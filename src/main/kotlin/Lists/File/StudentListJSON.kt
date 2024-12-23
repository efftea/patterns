package Lists.File

import Lists.SuperStudentListClass
import model.Student
import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StudentListJSON: SuperStudentListClass(), StudentListFileInterface {

    override fun readFromFile(path:String): MutableList<Student> {
        val listType = object : TypeToken<MutableList<Student>>() {}.type
        var gson = Gson()
        val file = File(path)
        var text:String = ""
        text = file.readText()
        return gson.fromJson(text,listType) ?: mutableListOf()
    }

    override fun writeToFile(students: MutableList<Student>, directory: String, fileName: String) {
        val file = File(directory, fileName)
        var gson = Gson()
        var json = gson.toJson(data)
        file.writeText(json)
    }
}