fun main(args: Array<String>) {
    val student1 = Student("Кислица", "Данил","Александрович","_79086761505","@efftea","kislica2003@mail.ru", "https://github.com/.efftea")
    val student2 = Student("Мищенко","Никита", "Максимович")

    println(student1.toString())
    println(student2.toString())

    println(student1.validate())
    println(student2.validate())

    student2.setContacts("77777777777","@efftea","mishenko@mail.ru")
    println(student2.toString())

    var student3Map= hashMapOf<String,Any?>(
        "lastName" to "Гринев",
        "firstName" to "Кирилл",
        "middleName" to "Владимирович"
    )
    var student3=Student(student3Map)
    println(student3.toString())
    

    var student4 = Student("Федоров Александр Геннадьевич")
    println(student4.toString())

    println(student1.getInfo())
}