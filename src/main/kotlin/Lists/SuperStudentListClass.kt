package Lists

import DataList
import Student
import StudentShort

open class SuperStudentListClass {
    var data:MutableList<Student> = mutableListOf()

    fun getById(id:Int): Student?
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
        var ss = s.map{ StudentShort(it) }
        return DataList(ss)
    }

    fun sortByShortname()
    {
        data.sortBy { it.getShortName() }
    }

    fun addStudent(stud: Student)
    {
        data.add(stud)
    }

    fun updateStudent(id:Int,stud: Student)
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

    fun studentCount():Int
    {
        return data.size
    }
}