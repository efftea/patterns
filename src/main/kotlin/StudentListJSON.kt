import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import StudentStrategy

class StudentListJSON: SuperStudentListClass(), StudentStrategy {

    override fun readFromFile(path:String)
    {
        val listType = object : TypeToken<MutableList<Student>>() {}.type
        var gson = Gson()
        val file = File(path)
        var text:String = ""
        try {
            text = file.readText()
        } catch (e: FileNotFoundException) {
            println("Файл не найден")
        } catch (e: IOException) {
            println("Ошибка при прочтении файла.")
        }
        data = gson.fromJson(text,listType) ?: mutableListOf()
    }

    override fun writeToFile(path:String)
    {
        var gson = Gson()
        var json = gson.toJson(data)
        val file = File(path)
        file.writeText(json)
    }
}