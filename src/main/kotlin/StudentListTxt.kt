import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import StudentListStrategy

class StudentListTxt: SuperStudentListClass(), StudentListStrategy {

    override fun readFromFile(path:String)
    {
        val file = File(path)
        var res = mutableListOf<Student>()
        var text:List<String> = listOf()
        try {
            text = file.readLines()
            println(text)
        } catch (e: FileNotFoundException) {
            println("Файл не найден")
        } catch (e: IOException) {
            println("Ошибка при прочтении файла.")
        }
        for (line in text)
        {
            res.add(Student(line.split(" ").get(0).toInt(),line.split(" ").get(1),line.split(" ").get(2),line.split(" ").get(3),line.split(" ").getOrNull(4),line.split(" ").getOrNull(5),line.split(" ").getOrNull(6),line.split(" ").getOrNull(7)))
        }
        data= res
    }

    override fun writeToFile(path: String)
    {
        val file = File(path)
        var text = ""
        for(stud in data)
        {
            text+=(stud.toString()+"\n")
        }
        file.writeText(text)
    }
}