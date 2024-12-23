package Lists.BD

import Lists.File.StudentListStrategy
import datalist.DataListStudentShort
import model.Student
import view.Filter

class StudentListAdapter(private val studentListF: StudentList, var filter: Filter? = null): StudentListInterface
{
    private var studentList: StudentListStrategy? = null

    override fun getById(id: Int): Student? {
        return studentList?.getById(id)
    }

    override fun getKNStudentShort(k: Int, n: Int): DataListStudentShort {
        studentListF.filter = this.filter
        return studentListF.getKNStudentShort(k,n,studentListF.filter) as DataListStudentShort
    }
    override fun addStudent(stud: Student): Int {
        studentList?.addStudent(stud)
        return stud.id
    }
    override fun updateStudent(id: Int, stud: Student): Boolean {
        getById(id) ?: return false
        studentList?.updateStudent(id,stud)
        return true
    }

    override fun deleteStudent(id: Int): Boolean {
        getById(id) ?: return false
        studentList?.deleteStudent(id)
        return true
    }
    override fun studentCount(): Int {
        return studentList?.studentCount()?:0
    }

    override fun initStudentFilter(sfilter: Filter?) {
        this.filter = sfilter
    }
}