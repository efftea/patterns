import Lists.*
import SQL.*

fun main(args: Array<String>) {
    var sl = StudentList("src/main/kotlin/input.txt")
    sl.addStudent(Student("Кислица", "Данил", "Александрович"))
    println(sl.getById(2).toString())
    println(sl.getById(1).toString())
    println(sl.studentCount())
}