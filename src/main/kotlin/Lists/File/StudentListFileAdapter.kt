package Lists.File

import Lists.BD.StudentListInterface
import datalist.DataListStudentShort
import model.Student
import view.Filter

class StudentListFileAdapter(
    private val studentListFile: StudentListFile,
    var studentFilter: Filter? = null
) : StudentListInterface {

    override fun getById(id: Int): Student? {
        return try {
            studentListFile.findById(id)
        } catch (e: NoSuchElementException) {
            null
        }
    }

    override fun getKNStudentShort(k: Int, n: Int): DataListStudentShort {
        studentListFile.studentFilter = this.studentFilter
        return studentListFile.get_k_n_student_short_list(n = n, k = k) as DataListStudentShort
    }

    override fun addStudent(student: Student): Int {
        studentListFile.add(student)
        return student.id
    }

    override fun updateStudent(id: Int, stud: Student): Boolean {
        getById(id) ?: return false
        studentListFile.replaceById(stud, id)
        return true
    }

    override fun deleteStudent(id: Int): Boolean {
        getById(id) ?: return false
        studentListFile.removeById(id)
        return true
    }

    override fun studentCount(): Int {
        return studentListFile.get_student_short_count()
    }

    override fun initStudentFilter(studentFilter: Filter?) {
        this.studentFilter = studentFilter
    }
}