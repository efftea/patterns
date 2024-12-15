import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonCreator

class Student: SuperStudentClass{
    @field:JsonProperty("lastName") var lastName: String = ""
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

    @field:JsonProperty("firstName") var firstName: String = ""
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

    @field:JsonProperty("middleName") var middleName: String = ""
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

    @field:JsonProperty("phone") var phone: String? = null
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

    @field:JsonProperty("telegram") var telegram: String? = null
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

    @field:JsonProperty("email") var email: String? = null
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

    @JsonCreator constructor(
        @JsonProperty("id") _id: String = "0",
        @JsonProperty("github") _github: String? = "",
        @JsonProperty("lastName") _lastName: String = "",
        @JsonProperty("firstName")  _firstName: String = "",
        @JsonProperty("middleName")  _middleName: String = "",
        @JsonProperty("phone")  _phone: String? = null,
        @JsonProperty("telegram")  _telegram: String? = null,
        @JsonProperty("email")  _email: String? = null,)
    {
        id=_id.toInt()
        lastName=_lastName
        firstName=_firstName
        middleName=_middleName
        phone=_phone
        telegram=_telegram
        email=_email
        github=_github
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

    constructor(_id:Int,_lastName:String,_firstName:String,_middleName:String,_phone:String?=null,_telegram:String?=null,_email:String?=null,_github:String?=null)
    {
        id = _id
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

    constructor(input:String): this (input.split(" ")[0],input.split(" ")[1],input.split(" ")[2],input.split(" ").getOrNull(3),input.split(" ").getOrNull(4),input.split(" ").getOrNull(5),input.split(" ").getOrNull(6))
    {
    }


    fun getInfo() : String
    {
        var res ="ФИО: "+ getShortName()
        if(hasGitHub())
        {
            res+= "Гит: "+ github
        }
        var res2 = ""
        if(hasContact())
        {
            if(email!=null)
            {
                res2 = " Почта: "+ email
            }
            if(phone!=null)
            {
                res2 = " Телефон: "+ phone
            }
            if(telegram!=null)
            {
                res2 = " Телеграм: "+ telegram
            }
        }
        return res + res2
    }

    fun getShortName(): String {
        var res = lastName+" "+ firstName[0]+"."+ middleName[0]+". "
        return res
    }

    override fun toString() : String
    {
        var out = "ID: $id"
        out+=", Фамилия: $lastName"
        out+=", Имя: $firstName"
        out+=", Отчество: $middleName"
        if(phone!=null)out+=", Телефон: $phone"
        if(email!=null)out+=", Почта: $email"
        if(github!=null)out+=", Гит: $github"
        return out
    }

    fun toString2() : String
    {
        var out = "$id $lastName $firstName $middleName"
        if(phone!=null)out+=" $phone"
        if(telegram!=null)out+=" $telegram"
        if(email!=null)out+=" $email"
        if(github!=null)out+=" $github"
        return out
    }
}