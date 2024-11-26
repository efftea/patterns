class Student{
    var id: Int = 0
        set(value)
        {
            if(value>0)
                field=value
        }
        get()
        {
            return field
        }

    var lastName: String = ""
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var firstName: String = ""
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var middleName: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var phone: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var telegram: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var email: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    var github: String? = null
        set(value)
        {
            field=value
        }
        get()
        {
            return field
        }

    fun write()
    {
        var out = "ID: $id"
        out+=", Фамиля: $lastName"
        out+=", Имя: $firstName"
        out+=", Отчество: $middleName"
        if(phone!=null)out+=", Телефон: $phone"
        if(email!=null)out+=", Почта: $email"
        if(github!=null)out+=", Гит: $github"
        println(out)
    }
}