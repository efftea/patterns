import SQL.*
import controller.studentListController
import view.*

fun main(args: Array<String>) {
    val view = app()
    val controller = studentListController(StudentsListDB(), view)
    view.create(controller)
}