class StudentShort {
    private var id: Int = -1
        get()
        {
            return field
        }
    private var shortname: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }
    private var github: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }
    private var contact: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }
    companion object
    {
        var ids=0
    }
    init
    {
        ids++
    }
    constructor(student:Student)
    {
        id=ids
        shortname=student.getInfo().split(" ")[1] + " " + student.getInfo().split(" ")[2]
        github=student.getInfo().split(" ").getOrNull(4)
        contact=student.getInfo().split(" ").getOrNull(6)
    }
    constructor(input:String)
    {
        id=ids
        shortname=input.split(" ").getOrNull(0)
        github=input.split(" ").getOrNull(1)
        contact=input.split(" ").getOrNull(2)
    }

    override fun toString() : String
    {
        var out = "ID: $id, ФИО: $shortname "
        if(github!=null)out+= ", Гит: $github "
        if(contact!=null)out+=", Контакт: $contact"
        return out
    }
}