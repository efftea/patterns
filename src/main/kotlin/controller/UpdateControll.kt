package controller

import Lists.BD.StudentList
import SQL.StudentsListDB
import model.Student
import model.SuperStudentClass

class UpdateControll(
    studentListController: studentListController,
    studentList: StudentList
) : FormControll(studentListController,studentList) {
    constructor(studentListController: studentListController) : this(
        studentListController,
        studentListController.getStudentsList()
    )

    override fun saveProcessedStudent(student: Student, id: Int?): String {
        if (id == null) {
            return "Ошибка, не найдено"
        }
        var isDo = true
        if (!SuperStudentClass.validateLastName(student.lastName)) {
            isDo = false
        }
        if (!SuperStudentClass.validateFirstName(student.firstName)) {
            isDo = false
        }
        if (!SuperStudentClass.validateMiddleName(student.middleName) && student.middleName != "") {
            isDo = false
        }
        if (!SuperStudentClass.validatePhone(student.phone) && student.phone != "") {
            isDo = false
        }
        if (!SuperStudentClass.validateTG(student.telegram) && student.telegram != "") {
            isDo = false
        }
        if (!SuperStudentClass.validateEMail(student.email) && student.email != "") {
            isDo = false
        }
        if (!SuperStudentClass.validateGitHub(student.github) && student.github != "") {
            isDo = false
        }
        if (isDo) {
            val oldStudentData = studentList.getById(id)
            if (oldStudentData != null) {
                studentList.updateStudent(id, student)
                studentListController.refresh_data()
                return "Студент обновлён!"
            } else {
                return "Ошибка, студент с данным ID не найден."
            }
        }
        else{
            return "Ошибка, неправильно заполнены данные."
        }
    }

    override fun getAccessFields(): ArrayList<String> {
        return arrayListOf("Фамилия", "Имя", "Отчество", "Номер", "Telegram", "GitHub", "Email")
    }
}