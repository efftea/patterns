package Lists.BD

import model.Student
import datalist.DataListStudentShort
import view.Filter

class StudentList(val studentList: StudentListInterface) {

    var filter: Filter? = null

    fun getById(id: Int): Student? {
        return studentList?.getById(id)
    }

    fun getKNStudentShort(k: Int, n: Int, studentFilter: Filter?): DataListStudentShort {
        this.filter = studentFilter

        if (this.filter != null) {
            this.studentList.initStudentFilter(studentFilter)
        }
        return studentList.getKNStudentShort(k, n)
    }

    fun addStudent(stud: Student): Int? {
        return studentList.addStudent(stud)
    }

    fun updateStudent(id: Int, stud: Student): Boolean {
        return studentList.updateStudent(id, stud)
    }

    fun deleteStudent(id: Int): Boolean {
        return studentList.deleteStudent(id)
    }

    fun studentCount(): Int {
        return studentList?.studentCount() ?: 0
    }
}