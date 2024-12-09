import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class StudentListTxt {
    var data:MutableList<Student> = mutableListOf()

    fun read_from_txt(path:String)
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

    fun write_to_txt(path: String)
    {
        val file = File(path)
        var text = ""
        for(stud in data)
        {
            text+=(stud.toString()+"\n")
        }
        file.writeText(text)
    }

    fun getById(id:Int):Student?
    {
        for(stud in data)
        {
            if(stud.id==id)
            {
                return stud
            }
        }
        return null
    }

    fun getKNStudentShort(k: Int, n: Int) : DataList<StudentShort>
    {
        var s = data.subList((k-1)*n+1,n)
        var ss = s.map{StudentShort(it)}
        return DataList(ss)
    }

    fun sortByShortname()
    {
        data.sortBy { it.getShortName() }
    }

    fun addStudent(stud:Student)
    {
        data.add(stud)
    }

    fun replaceStudent(id:Int,stud: Student)
    {
        var st = getById(id)
        var i=data.indexOf(st)
        data.set(i,stud)
    }

    fun deleteStudent(id:Int)
    {
        var st = getById(id)
        var i=data.indexOf(st)
        data.removeAt(i)
    }

    fun getStudentShortCount():Int
    {
        return data.size
    }
}