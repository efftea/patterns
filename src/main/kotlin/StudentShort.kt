import org.postgresql.jdbc.PgResultSet.toInt

class StudentShort : SuperStudentClass{
    var shortname: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var contact: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    constructor(student:Student)
    {
        id=student.id
        shortname=student.getInfo().split(" ")[1] + " " + student.getInfo().split(" ")[2]
        github=student.getInfo().split(" ").getOrNull(4)
        contact=student.getInfo().split(" ").getOrNull(6)
    }

    constructor(input:String)
    {
        id=ids
        shortname=input.split(" ").getOrNull(1)
        github=input.split(" ").getOrNull(2)
        contact=input.split(" ").getOrNull(3)
    }

    override fun toString() : String
    {
        var out = "ID: $id, ФИО: $shortname "
        if(github!=null)out+= ", Гит: $github "
        if(contact!=null)out+=", Контакт: $contact"
        return out
    }
}