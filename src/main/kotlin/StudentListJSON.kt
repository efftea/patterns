import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StudentListJSON {
    var data:MutableList<Student> = mutableListOf()
    fun readFromFile(path:String)
    {
        val listType = object : TypeToken<MutableList<Student>>() {}.type
        var gson = Gson()
        data = gson.fromJson(path,listType) ?: mutableListOf()
    }
    fun writeToFile(path:String)
    {
        var gson = Gson()
        var json = gson.toJson(data)
        val file = File(path)
        file.writeText(json)
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