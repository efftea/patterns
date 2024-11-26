fun main(args: Array<String>) {
    val student1 = Student().apply {
        id = 1
        lastName = "Кислица"
        firstName = "Данил"
        middleName = "Александрович"
        phone = "79086761505"
        telegram = "@efftea"
        email = "kislica2003@mail.ru"
        github = "efftea"
    }

    val student2 = Student().apply {
        id = 2
        lastName = "Мищенко"
        firstName = "Никита"
        middleName = "Максимович"
    }

    student1.write()
    student2.write()
}