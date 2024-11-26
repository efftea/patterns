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

    companion object
    {
        var ids=0
        fun validatePhone(value:String?): Boolean
        {
            return value?.matches(Regex("""\+?\d{11}""")) ?: true
        }
    }
    init
    {
        ids++
    }

    constructor(_lastName:String,_firstName:String,_middleName:String)
    {
        id=ids
        lastName=_lastName
        firstName=_firstName
        middleName=_middleName
    }

    constructor(_lastName:String,_firstName:String,_middleName:String,_phone:String?=null,_telegram:String?=null,_email:String?=null,_github:String?=null)
    {
        id=ids
        lastName=_lastName
        firstName=_firstName
        middleName=_middleName
        phone=_phone
        telegram=_telegram
        email=_email
        github=_github
    }

    constructor(hashStud: HashMap<String,Any?>)
    {
        id=ids
        lastName=hashStud["lastName"].toString()
        firstName=hashStud["firstName"].toString()
        middleName=hashStud["middleName"].toString()
        phone=hashStud.getOrDefault("phone",null).toString()
        telegram=hashStud.getOrDefault("telegram",null).toString()
        email=hashStud.getOrDefault("email",null).toString()
        github=hashStud.getOrDefault("github",null).toString()
    }

    override fun toString() : String
    {
        var out = "ID: $id"
        out+=", Фамиля: $lastName"
        out+=", Имя: $firstName"
        out+=", Отчество: $middleName"
        if(phone!=null)out+=", Телефон: $phone"
        if(email!=null)out+=", Почта: $email"
        if(github!=null)out+=", Гит: $github"
        return out
    }
}