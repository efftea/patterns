package model

class StudentShort : SuperStudentClass {
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


    constructor(student: Student)
    {
        id=student.id
        shortname=student.getShortName()
        github=student.getGitHubInfo()
        contact=student.getContactInfo()
    }

    constructor(input:String)
    {
        id= SuperStudentClass.ids
        shortname=input.split(" ").getOrNull(1)
        github=input.split(" ").getOrNull(2)
        contact=input.split(" ").getOrNull(3)
    }

    override fun getShortNameInfo(): String? {
        return this.shortname
    }

    override fun getContactInfo(): String? {
        return this.contact
    }

    override fun getGitHubInfo(): String? {
        return this.github
    }

    override fun toString() : String
    {
        var out = "ID: $id, ФИО: $shortname "
        if(github!=null)out+= ", Гит: $github "
        if(contact!=null)out+=", Контакт: $contact"
        return out
    }
}