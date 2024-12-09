import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class StudentListTxt {
    companion object
    {
        fun read_from_txt(path:String): MutableList<Student>
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
                res.add(Student(line))
            }
            return res
        }

        fun write_to_txt(path: String, studentList:MutableList<Student>)
        {
            val file = File(path)
            var text = ""
            for(stud in studentList)
            {
                text+=(stud.toString()+"\n")
            }
            file.writeText(text)
        }
    }
}