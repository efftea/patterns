package Lists

import datalist.DataListStudentShort
import student.Student
import student.StudentShort
import view.Filter

interface StudentListInterface
{
    fun getById(id:Int): Student?
    fun getKNStudentShort(k: Int, n: Int) : DataListStudentShort
    fun addStudent(stud: Student): Int
    fun updateStudent(id:Int,stud: Student)
    fun initStudentFilter(studentFilter: Filter?)
    fun deleteStudent(id:Int)
    fun studentCount():Int
}