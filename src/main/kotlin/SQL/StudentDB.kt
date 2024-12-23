package SQL

import Lists.StudentListInterface
import student.StudentShort
import student.Student
import datalist.*
import view.Filter
import view.Params
import java.sql.*


class StudentsListDB(var filter: Filter? = null) :StudentListInterface {
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


    override fun addStudent(student: Student): Int {
        var input = "'${student.lastName}', '${student.firstName}', '${student.middleName}'"
        if (student.phone == null) {
            input += ", NULL"
        } else {
            input += ", '${student.phone}'"
        }
        if (student.telegram == null) {
            input += ", NULL"
        } else {
            input += ", '${student.telegram}'"
        }
        if (student.email == null) {
            input += ", NULL"
        } else {
            input += ", '${student.email}'"
        }
        if (student.github == null) {
            input += ", NULL"
        } else {
            input += ", '${student.github}'"
        }
        println(input)
        val result =
            executeQuery("INSERT INTO student (lastName, firstName, middleName, phone, telegram, email, github) VALUES (${input});")
        if (result != null) {
            if (result.next()) {
                return result.getInt("id")
            } else {
                throw Exception("Failed")
            }
        }
        return TODO("Failed")
    }


    override fun updateStudent(id:Int,student: Student): Boolean
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
        return true
    }

    override fun initStudentFilter(studentFilter: Filter?) {
        TODO("Not yet implemented")
    }

    override fun deleteStudent(id:Int): Boolean
    {
        executeQuery("DELETE FROM student WHERE id=${id};")
        return true
    }

    override fun studentCount():Int
    {
        val result=executeQuery("SELECT COUNT(*) FROM student;")
        if(result!=null)
        {
            println(result)
            if(result.next())
                return result.getString("count").toInt()
        }
        return 0
    }

    private fun updateFilterQuery(
        query: String,
        search: Params,
        value: String,
        column_name: String
    ): String {
        var new_query = query
        if (search == Params.YES) {
            new_query += " AND $column_name IS NOT NULL AND $column_name!=''"
            if (value.isNotEmpty()) {
                new_query += " AND $column_name LIKE '%$value%'"
            }
        } else {
            if (search == Params.NO) new_query += " AND ($column_name IS NULL OR $column_name='')"
        }

        return new_query
    }

    private fun filterQuery(filter: Filter): String {
        var query = "WHERE (TRUE"
        val nameFilter = filter.nameFilter
        if (nameFilter.isNotEmpty()) query += " AND last_name || ' ' || first_name ILIKE '%$nameFilter%'"
        query = updateFilterQuery(
            query,
            filter.gitSearch,
            filter.gitFilter,
            "github"
        )
        query = updateFilterQuery(
            query,
            filter.emailSearch,
            filter.emailFilter,
            "email"
        )
        query = updateFilterQuery(
            query,
            filter.phoneSearch,
            filter.phoneFilter,
            "phone"
        )
        query = updateFilterQuery(
            query,
            filter.telegramSearch,
            filter.telegramFilter,
            "telegram"
        )

        return "$query)"
    }

    override fun getKNStudentShort(k:Int, n:Int): DataListStudentShort
    {
        val page = n
        val pageSize = k
        if (this.filter != null) {
            return DataListStudentShort(getFilterStudentList(page = pageSize, pageSize = pageSize, filter!!), 1)
        }
        val offset = (page - 1) * pageSize
        val query = "SELECT * FROM student ORDER BY id LIMIT $pageSize OFFSET $offset"
        val studentShortList = mutableListOf<StudentShort>()

        val result = executeQuery(query)
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
        var ss = sl.map{ StudentShort(it) }
        return DataListStudentShort(ss)
    }

    fun getFilterStudentList(
        page: Int,
        pageSize: Int,
        filter: Filter,
    ): List<Student> {
        val offset = (page - 1) * pageSize
        val result = executeQuery("SELECT * FROM student ${filterQuery(filter)} ORDER BY id LIMIT $pageSize OFFSET $offset")
        val sl = mutableListOf<Student>()

        if (result != null) {
            while (result.next()) {
                val input = StringBuilder()
                for (i in 2..result.metaData.columnCount) { // Начинаем с 1
                    input.append(result.getString(i)).append(" ")
                }
                sl.add(Student(input.toString(), result.getInt(1))) // Добавляем студента
            }
        }
        return sl
    }

    fun getFilterCount(studentFilter: Filter?): Int {
        var query = "SELECT COUNT(*) FROM student ${filterQuery(studentFilter!!)}"

        try {
            val results = executeQuery(query)
            if(results != null) {
                if (results.next()) {
                    return results.getInt(1)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return 0
    }
}