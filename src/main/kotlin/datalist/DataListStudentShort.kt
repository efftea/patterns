package datalist

import datalist.DataList
import observer.observerServ
import observer.observer
import student.Student
import student.StudentShort

class DataListStudentShort(studentList: List<StudentShort>) : DataList<StudentShort>(studentList), observerServ {

    constructor(studentsList: List<Student>, count: Int) : this(studentsList.map { StudentShort(it) })

    override val observers: MutableList<observer> = mutableListOf()

    override fun getNames(): List<String>
    {
        return listOf("№","Фамилия Инициалы","Git","Контакт")
    }

    override fun getDataOfRows():MutableList<MutableList<Any?>>
    {
        var args= mutableListOf<MutableList<Any?>>()
        args.add(mutableListOf())
        var count=1
        for (row in data)
        {
            args.add(mutableListOf(count,row.shortname,row.github,row.contact))
            count++
        }
        return args
    }
}