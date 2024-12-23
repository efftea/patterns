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
    val controller = studentListController(StudentsListDB(), view)
    view.create(controller)
}