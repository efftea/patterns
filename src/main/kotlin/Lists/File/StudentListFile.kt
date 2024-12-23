package Lists.File

import Lists.BD.StudentListInterface
import datalist.DataList
import datalist.DataListStudentShort
import model.Student
import model.StudentShort
import view.Filter
import view.Params

class StudentListFile(
    private var students: MutableList<Student>,
    var fileProcessor: StudentListFileInterface = StudentListTxt()
    ) {
        constructor(
            fileProcessor: StudentListFileInterface = StudentListTxt()
        ) : this(mutableListOf(), fileProcessor)

        constructor(
            filePath: String,
            fileProcessor: StudentListFileInterface = StudentListTxt()
        ) : this(mutableListOf(), fileProcessor) {
            readFromFile(filePath)
        }

        var studentFilter: Filter? = null;

        fun readFromFile(filePath: String) {
            students = fileProcessor.readFromFile(filePath)
        }

        fun writeToFile(directory: String, fileName: String) {
            fileProcessor.writeToFile(students, directory, fileName)
        }

        fun findById(id: Int): Student {
            return students.first { it.id == id }
        }

        fun get_k_n_student_short_list(n: Int, k: Int): DataList<StudentShort> {
            require(n >= 0) { "Индекс n должен быть больше или равен 0." }
            require(k > 0) { "Количество k должно быть больше 0." }

            if (studentFilter != null) {
                return DataListStudentShort(
                    students
                        .drop((n - 1) * k)
                        .take(k)
                        .stream()
                        .filter { filterByStudentFilter(it) }
                        .map { StudentShort(it) }
                        .toList()
                )
            }
            return DataListStudentShort(
                students
                    .drop((n - 1) * k)
                    .take(k)
                    .map { StudentShort(it) }
            )
        }

        fun filterByStudentFilter(student: Student): Boolean {
            if (studentFilter == null) {
                return true;
            }

            if (studentFilter!!.nameFilter.isNotEmpty() && !student.getFullName().contains(studentFilter!!.nameFilter)) {
                return false;
            } else if (
                !filterValueAndSearchParam(student.email, studentFilter!!.emailFilter, studentFilter!!.emailSearch) ||
                !filterValueAndSearchParam(student.github, studentFilter!!.gitFilter, studentFilter!!.gitSearch) ||
                !filterValueAndSearchParam(student.phone, studentFilter!!.phoneFilter, studentFilter!!.phoneSearch) ||
                !filterValueAndSearchParam(student.telegram, studentFilter!!.telegramFilter, studentFilter!!.telegramSearch)
            ) {
                return false
            }

            return true
        }

        fun filterValueAndSearchParam(value: String?, filterValue: String, searchParam: Params): Boolean {
            if (
                searchParam == Params.YES &&
                (
                        (
                                filterValue.isNotEmpty() && value?.contains(filterValue) != true
                                ) ||
                                (
                                        filterValue.isEmpty() && value.isNullOrEmpty()
                                        )
                        )
            ) {
                return false
            } else if (
                searchParam == Params.NO &&
                !value.isNullOrEmpty()
            ) {
                return false
            }
            return true
        }

        fun orderStudentsByLastNameInitials() {
            orderStudents(compareBy { it.getShortName() })
        }

        fun orderStudents(comparator: Comparator<Student>) {
            students.sortedWith(comparator)
        }

        fun add(student: Student) {
            val nextId = (students.maxByOrNull { it.id }?.id ?: 0) + 1
            student.id = nextId

            students.addLast(student)
        }

        fun replaceById(student: Student, id: Int) {
            student.id = id
            students.replaceAll { if (it.id == id) student else it }
        }

        fun removeById(id: Int) {
            students.removeIf { it.id == id }
        }

        fun get_student_short_count(): Int = students.count()
}

private fun <E> MutableList<E>.addLast(student: E) {

}
