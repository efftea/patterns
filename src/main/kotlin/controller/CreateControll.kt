package controller

import Lists.StudentList
import SQL.StudentsListDB
import student.Student

class CreateControll(
    val studentListController: studentListController,
    val studentList: StudentList
): FormControll() {

    constructor(studentListController: studentListController): this(studentListController, StudentList(StudentsListDB()))

    override fun saveProcessedStudent(student: Student): String {
        val id = studentList.addStudent(student)
        if (id > 0) {
            studentListController.refresh_data()
            return "Студент добавлен!"
        } else {
            return "Ошибка при добавлении студента."
        }
    }
}