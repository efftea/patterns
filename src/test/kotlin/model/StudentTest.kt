package model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class StudentTest {

    private lateinit var student: Student

    @BeforeEach
    fun setUp() {
        student = Student()
    }

    @org.junit.jupiter.api.Test
    fun getInfo() {
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"
        student.phone = "+1234567890"
        student.email = "kislica2003@mail.ru"
        student.telegram = "@efftea"
        student.github = "https://github.com/.efftea"

        val expected = "ФИО: Кислица Д.А. Гит: https://github.com/.efftea Телеграм: @efftea"
        assertEquals(expected, student.getInfo())
    }

    @org.junit.jupiter.api.Test
    fun getShortName() {
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"

        val expected = "Кислица Д.А."
        assertEquals(expected, student.getShortName())
    }

    @org.junit.jupiter.api.Test
    fun testToString() {
        student.id = 2
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"
        student.phone = "89086761505"
        student.email = "kislica2003@mail.ru"
        student.telegram = "@efftea"
        student.github = "https://github.com/.efftea"

        val expected = "ID: 2, Фамилия: Кислица, Имя: Данил, Отчество: Александрович, Телеграм: @efftea, Телефон: 89086761505, Почта: kislica2003@mail.ru, Гит: https://github.com/.efftea"
        assertEquals(expected, student.toString())
    }

    @org.junit.jupiter.api.Test
    fun testToString2() {
        student.id = 2
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"
        student.phone = "89086761505"
        student.email = "kislica2003@mail.ru"

        student.github = "https://github.com/.efftea"

        val expected = "ID: 2, Фамилия: Кислица, Имя: Данил, Отчество: Александрович, Телефон: 89086761505, Почта: kislica2003@mail.ru, Гит: https://github.com/.efftea"
        assertEquals(expected, student.toString())
    }

    @org.junit.jupiter.api.Test
    fun testToString3() {
        student.id = 2
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"
        student.email = "kislica2003@mail.ru"
        student.github = "https://github.com/.efftea"

        val expected = "ID: 2, Фамилия: Кислица, Имя: Данил, Отчество: Александрович, Почта: kislica2003@mail.ru, Гит: https://github.com/.efftea"
        assertEquals(expected, student.toString())
    }

    @org.junit.jupiter.api.Test
    fun testToString4() {
        student.id = 2
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"
        student.github = "https://github.com/.efftea"

        val expected = "ID: 2, Фамилия: Кислица, Имя: Данил, Отчество: Александрович, Гит: https://github.com/.efftea"
        assertEquals(expected, student.toString())
    }

    @org.junit.jupiter.api.Test
    fun testToString5() {
        student.id = 2
        student.lastName = "Кислица"
        student.firstName = "Данил"
        student.middleName = "Александрович"

        val expected = "ID: 2, Фамилия: Кислица, Имя: Данил, Отчество: Александрович"
        assertEquals(expected, student.toString())
    }
}