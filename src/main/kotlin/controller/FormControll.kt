package controller

import student.Student;

abstract class FormControll {

    fun processForm(
        existingStudent: Student?,
        lastName: String,
        firstName: String,
        middleName: String,
        telegram: String,
        git: String,
        email: String
    ): Student {
        val student = if (existingStudent != null) existingStudent else Student()
        student.lastName = lastName
        student.firstName = firstName
        student.middleName = middleName
        student.telegram = telegram
        student.github = git
        student.email = email
        student.validate()

        return student
    }

    abstract fun saveProcessedStudent(student: Student): String
}