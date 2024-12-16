package Lists

import Student
import StudentShort
import DataList
import Lists.StudentListJSON
import Lists.StudentListTxt
import Lists.StudentListYAML
import Lists.StudentListStrategy
import SQL.StudentsListDB

interface StudentListInterface
{
    fun getById(id:Int):Student?
    fun getKNStudentShort(k: Int, n: Int) : DataList<StudentShort>
    fun addStudent(stud:Student)
    fun updateStudent(id:Int,stud: Student)
    fun deleteStudent(id:Int)
    fun studentCount():Int
}

class StudentListAdapter(var path:String):StudentListInterface
{
    private var studentList: StudentListStrategy? = null
    init {
        if (path.split('.')[1]=="txt")
            studentList= StudentListStrategy(StudentListTxt())
        if (path.split('.')[1]=="json")
            studentList= StudentListStrategy(StudentListJSON())
        if (path.split('.')[1]=="yaml")
            studentList= StudentListStrategy(StudentListYAML())
        studentList?.readFromFile(path)
    }
    override fun getById(id: Int): Student? {
        return studentList?.getById(id)
    }
    override fun getKNStudentShort(k: Int, n: Int): DataList<StudentShort> {
        return studentList?.getKNStudentShort(k,n) ?:DataList(mutableListOf())
    }
    override fun addStudent(stud: Student) {
        studentList?.addStudent(stud)
    }
    override fun updateStudent(id: Int, stud: Student) {
        studentList?.updateStudent(id,stud)
    }
    override fun deleteStudent(id: Int) {
        studentList?.deleteStudent(id)
    }
    override fun studentCount(): Int {
        return studentList?.studentCount()?:0
    }
}

class StudentList(path: String):StudentListInterface {
    private var studentList: StudentListInterface? = null
    init{
        if(path=="pg")
        {
            studentList=StudentsListDB.getInstance()
        }
        else
        {
            studentList=StudentListAdapter(path)
        }
    }
    override fun getById(id: Int): Student? {
        return studentList?.getById(id)
    }
    override fun getKNStudentShort(k: Int, n: Int): DataList<StudentShort> {
        return studentList?.getKNStudentShort(k,n) ?:DataList(mutableListOf())
    }
    override fun addStudent(stud: Student) {
        studentList?.addStudent(stud)
    }
    override fun updateStudent(id: Int, stud: Student) {
        studentList?.updateStudent(id,stud)
    }
    override fun deleteStudent(id: Int) {
        studentList?.deleteStudent(id)
    }
    override fun studentCount(): Int {
        return studentList?.studentCount()?:0
    }
}