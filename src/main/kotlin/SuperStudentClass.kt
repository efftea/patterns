open class SuperStudentClass {
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
    var github: String? = null
        set(value)
        {
            if(validateGitHub(value))
            {
                field=value
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
    init
    {
        ids++
    }
}