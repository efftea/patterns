package controller

import Lists.StudentList
import SQL.StudentsListDB
import model.Student

class CreateControll(
    studentListController: studentListController,
    studentList: StudentList
): FormControll(studentListController,studentList) {

    constructor(studentListController: studentListController): this(studentListController, StudentList(StudentsListDB()))

    override fun saveProcessedStudent(student: Student, id: Int?): String {
        val id = studentList.addStudent(student)
        if (id > 0) {
            studentListController.refresh_data()
            return "Студент добавлен!"
        } else {
            return "Ошибка при добавлении студента."
        }
    }

    override fun getAccessFields(): ArrayList<String> {
        return arrayListOf("Фамилия", "Имя", "Отчество", "Telegram", "GitHub", "Email")
    }
}