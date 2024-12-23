package controller

import view.app
import Lists.*;
import datalist.DataListStudentShort
import view.Filter


class studentListController(studentData: StudentListInterface, private var view: app)  {
    private var studentsList: StudentList = StudentList(studentData);
    private var dataListStudentShort: DataListStudentShort? = null;

    fun setView(view: app) {
        this.view = view
    }

    fun firstInitDataList() {
        val page = 1
        val pageSize = 20
        dataListStudentShort = studentsList.getKNStudentShort(pageSize, page)
        dataListStudentShort?.pagination?.updatePagination(
            studentsList.studentCount(),
            page,
            pageSize
        )
        view.setDataList(dataListStudentShort)
    }

    fun refresh_data(pageSize: Int, page: Int, studentFilter: Filter?) {
        dataListStudentShort = studentsList.getKNStudentShort(pageSize, page);
        dataListStudentShort?.pagination?.updatePagination(
            studentsList.studentCount(),
            page,
            pageSize
        )
        view.setDataList(dataListStudentShort)
        dataListStudentShort?.addObserver(view)
        dataListStudentShort?.notifyObservers()
    }

    fun refresh_data() {
        val page = dataListStudentShort?.pagination?.currentPage
        val pageSize = dataListStudentShort?.pagination?.perPage
        val studentFilter = studentsList.filter
        if (page == null || pageSize == null) {
            return
        }
        dataListStudentShort = studentsList.getKNStudentShort(k = pageSize, n = page, studentFilter = studentFilter);
        dataListStudentShort?.pagination?.updatePagination(
            studentsList.studentCount(),
            page,
            pageSize
        )
        view.setDataList(dataListStudentShort)
        dataListStudentShort?.addObserver(view)
        dataListStudentShort?.notifyObservers()
    }

    private fun throwErrorMessage(errorMessage: String?) {
        var error = ""
        if (errorMessage == null) {
            error = "Неизвестная ошибка"
        } else {
            error = errorMessage
        }
        val page = 1
        val pageSize = 20
        dataListStudentShort = DataListStudentShort(mutableListOf())
        dataListStudentShort?.pagination?.updatePagination(
            0,
            page,
            pageSize
        )
        view.setDataList(dataListStudentShort)
    }

    fun deleteStudent(id: Int): Boolean {
        return studentsList.deleteStudent(id)
    }
}