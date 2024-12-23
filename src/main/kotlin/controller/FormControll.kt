package controller

import model.Student;
import SQL.StudentsListDB
import Lists.BD.StudentList

abstract class FormControll(
    val studentListController: studentListController,
    val studentList: StudentList
) {
    constructor(studentListController: studentListController) : this(
        studentListController,
        studentListController.getStudentsList()
    )

    fun processForm(
        existingStudent: Student?,
        lastName: String,
        firstName: String,
        middleName: String?,
        phone: String?,
        telegram: String?,
        git: String?,
        email: String?
    ): Student {
        val student = if (existingStudent != null) existingStudent else Student()
        student.lastName = lastName
        student.firstName = firstName
        student.middleName = middleName
        student.phone = phone
        student.telegram = telegram
        student.github = git
        student.email = email
        student.validate()

        return student
    }

    fun getStudentById(id: Int): Student? {
        return studentList.getById(id)
    }
    abstract fun saveProcessedStudent(student: Student, id: Int?): String

    abstract fun getAccessFields(): ArrayList<String>
}