package SQL
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

import Lists.StudentListInterface
import StudentShort
import Student
import DataList

class StudentsListDB private constructor():StudentListInterface {
    companion object {
        @Volatile
        private var instance: StudentsListDB? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: StudentsListDB().also { instance = it }
            }
    }

    private lateinit var connection: Connection
    init {
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/4lr",
                "postgres",
                "admin"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun executeQuery(query: String): ResultSet? {
        return try {
            val stmt = connection.createStatement()
            stmt.executeQuery(query)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override fun getById(id: Int): Student? {
        val result = executeQuery("SELECT * FROM student WHERE id = ${id}")
        var input = ""
        var id = 0
        if (result != null) {
            // Вывод каждой строки
            while (result.next()) {
                input = ""
                id=result.getString(1).toInt()
                for (i in 2..result.metaData.columnCount) {
                    input+=result.getString(i)+" "
                }
            }
            return Student(input, id)
        }
        return null
    }

    override fun getKNStudentShort(k:Int, n:Int):DataList<StudentShort>
    {
        val start = k*n
        val result = executeQuery("SELECT * FROM student WHERE id > ${start} ORDER BY id LIMIT ${n};")
        var input = ""
        var sl=mutableListOf<Student>()
        if (result != null) {
            while (result.next()) {
                input = ""
                for (i in 2..result.metaData.columnCount) {
                    input+=result.getString(i)+" "
                }
                sl.add(Student(input,result.getInt(1)))
            }
        }
        var ss = sl.map{StudentShort(it)}
        return DataList(ss)
    }

    override fun addStudent(student:Student)
    {
        var input = "'${student.lastName}', '${student.firstName}', '${student.middleName}'"
        if(student.phone==null){input+=", NULL"}
        else{input+=", '${student.phone}'"}
        if(student.telegram==null){input+=", NULL"}
        else{input+=", '${student.telegram}'"}
        if(student.email==null){input+=", NULL"}
        else{input+=", '${student.email}'"}
        if(student.github==null){input+=", NULL"}
        else{input+=", '${student.github}'"}
        println(input)
        executeQuery("INSERT INTO student (lastName, firstName, middleName, phone, telegram, email, github) VALUES (${input});")
    }

    override fun updateStudent(id:Int,student: Student)
    {
        var input = "'${student.lastName}', '${student.firstName}', '${student.middleName}'"
        if(student.phone==null){input+=", NULL"}
        else{input+=", '${student.phone}'"}
        if(student.telegram==null){input+=", NULL"}
        else{input+=", '${student.telegram}'"}
        if(student.email==null){input+=", NULL"}
        else{input+=", '${student.email}'"}
        if(student.github==null){input+=", NULL"}
        else{input+=", '${student.github}'"}
        executeQuery("UPDATE student SET (lastName, firstName, middleName, phone, telegram, email, github) = (${input}) WHERE id=${id};")
    }

    override fun deleteStudent(id:Int)
    {
        executeQuery("DELETE FROM student WHERE id=${id};")
    }

    override fun studentCount():Int
    {
        val result=executeQuery("SELECT COUNT(*) FROM student;")
        if(result!=null)
        {
            if(result.next())
                return result.getString("count").toInt()
        }
        return 0
    }
}