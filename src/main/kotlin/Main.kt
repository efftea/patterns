import Lists.BD.StudentListAdapter
import Lists.File.StudentListFile
import Lists.File.StudentListFileAdapter
import Lists.File.StudentListJSON
import Lists.SuperStudentListClass
import SQL.*
import controller.studentListController
import view.*

fun main(args: Array<String>) {
    val view = app()
//    val controller = studentListController(StudentsListDB(), view)
    val filePath = "src/main/kotlin/Lists/files/input.json"
    val studentListFile = StudentListFile(filePath, StudentListJSON())
    val controller = studentListController(StudentListFileAdapter(studentListFile), view)
    view.create(controller)
}