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
            if(validateLastName(value))
            {
                field=value
            }
        }
        get()
        {
            return field
        }

    var firstName: String = ""
        set(value)
        {
            if(validateFirstName(value))
            {
                field=value
            }
        }
        get()
        {
            return field
        }

    var middleName: String = ""
        set(value)
        {
            if(validateMiddleName(value))
            {
                field=value
            }
        }
        get()
        {
            return field
        }

    var phone: String? = null
        set(value)
        {
            if(validatePhone(value)) {
                field = value
            }
        }
        get()
        {
            return field
        }

    var telegram: String? = null
        set(value)
        {
            if(validateTG(value)) {
                field = value
            }
        }
        get()
        {
            return field
        }

    var email: String? = null
        set(value)
        {
            if(validateEMail(value)) {
                field = value
            }
        }
        get()
        {
            return field
        }

    var github: String? = null
        set(value)
        {
            if(validateGitHub(value)) {
                field = value
            }
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

        fun validateLastName(value:String): Boolean
        {
            return value.matches(Regex("""[A-Я]{1}[a-я]*"""))
        }
        fun validateFirstName(value:String): Boolean
        {
            return value.matches(Regex("""[A-Я]{1}[a-я]*"""))
        }
        fun validateMiddleName(value:String): Boolean
        {
            return value.matches(Regex("""[A-Я]{1}[a-я]*"""))
        }

        fun validateTG(value:String?): Boolean
        {
            return value?.matches(Regex("""\@{1}.*""")) ?: true
        }
        fun validateEMail(value:String?): Boolean
        {
            return value?.matches(Regex("""\w*\@\w*\.\w*""")) ?: true
        }
        fun validateGitHub(value:String?): Boolean
        {
            return value?.matches(Regex("""https://github.com/.*""")) ?: true
        }

    }

    fun validate() : Boolean
    {
        return this.hasGitHub() && this.hasContact()
    }
    private fun hasGitHub() : Boolean
    {
        return this.github!=null
    }
    private fun hasContact() : Boolean
    {
        return this.phone!=null || this.telegram!=null || this.email!=null
    }

    fun setContacts(_phone: String?=null,_telegram: String?=null,_mail: String?=null)
    {
        if(_phone!=null&&validatePhone(_phone))
        {
            phone = _phone
        }
        if(_telegram!=null&&validateTG(_telegram))
        {
            telegram = _telegram
        }
        if(_mail!=null&&validateEMail(_mail))
        {
            email = _mail
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