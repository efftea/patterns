package Lists.BD

import datalist.DataListStudentShort
import model.Student
import view.Filter

interface StudentListInterface
{
    fun getById(id:Int): Student?
    fun getKNStudentShort(k: Int, n: Int) : DataListStudentShort
    fun addStudent(stud: Student): Int
    fun updateStudent(id:Int,stud: Student): Boolean
    fun deleteStudent(id:Int): Boolean
    fun studentCount():Int
    fun initStudentFilter(studentFilter: Filter?)
}