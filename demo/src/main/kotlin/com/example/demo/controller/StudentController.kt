package com.example.demo.controller

import com.example.demo.repository.StudentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class StudentController(val studentRepository: StudentRepository) {

    @GetMapping("/students")
    fun listStudents(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "15") size: Int
    ): String {
        val pageable: Pageable = PageRequest.of(page, size)
        val studentPage = studentRepository.findAll(pageable)

        val formattedStudents = studentPage.content.map { student ->
            mapOf(
                "id" to student.id,
                "fullname" to "${student.lastname} ${student.firstname.firstOrNull() ?: ""}.${student.surname?.firstOrNull() ?: ""}.",
                "git" to student.git.orEmpty(),
                "contact" to (student.telegram ?: student.phone ?: student.email ?: "N/A")
            )
        }

        model.addAttribute("students", formattedStudents)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", studentPage.totalPages)
        return "students"
    }
}
