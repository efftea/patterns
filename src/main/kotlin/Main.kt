import Lists.*
import SQL.*
import java.io.PrintStream
import App

fun main(args: Array<String>) {
    val dbConnection = StudentsListDB()
    val result = dbConnection.executeQuery("SELECT * FROM student")

    if (result != null) {
        App.create()
    }

}