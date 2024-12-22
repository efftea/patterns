package controller

import Lists.StudentList
import SQL.StudentsListDB
import student.Student

class UpdateControll(
    studentListController: studentListController,
    studentList: StudentList
) : FormControll(studentListController,studentList) {
    constructor(studentListController: studentListController) : this(
        studentListController,
        StudentList(StudentsListDB())
    )

    override fun saveProcessedStudent(student: Student, id: Int?): String {
        if (id == null) {
            return "Ошибка, не найдено"
        }
        val oldStudentData = studentList.getById(id)
        if (oldStudentData != null) {
            val success = studentList.updateStudent(id, student)
            if (success!=null) {
                studentListController.refresh_data()
                return "Студент обновлён!"
            } else {
                return "Ошибка при обновлении студента"
            }
        } else {
            return "Ошибка, студент с данным ID не найден."
        }
    }

    override fun getAccessFields(): ArrayList<String> {
        return arrayListOf("Фамилия", "Имя", "Отчество")
    }
}