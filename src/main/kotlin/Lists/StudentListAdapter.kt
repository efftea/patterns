package Lists

import datalist.DataListStudentShort
import student.Student
import view.Filter

class StudentListAdapter(private val studentListF: StudentList, var filter: Filter? = null):StudentListInterface
{
    private var studentList: StudentListStrategy? = null

    override fun getById(id: Int): Student? {
        return studentList?.getById(id)
    }

    override fun getKNStudentShort(k: Int, n: Int): DataListStudentShort {
        studentListF.filter = this.filter
        return studentListF.getKNStudentShort(k,n) as DataListStudentShort
    }
    override fun addStudent(stud: Student): Int {
        studentList?.addStudent(stud)
        return stud.id
    }
    override fun updateStudent(id: Int, stud: Student) {
        studentList?.updateStudent(id,stud)
    }

    override fun initStudentFilter(studentFilter: Filter?) {
        this.filter = studentFilter
    }

    override fun deleteStudent(id: Int) {
        studentList?.deleteStudent(id)
    }
    override fun studentCount(): Int {
        return studentList?.studentCount()?:0
    }
}