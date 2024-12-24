package com.example.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
@Table(name = "student")
data class Student(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val lastname: String,
    val firstname: String,
    val surname: String?,
    val telegram: String?,
    val phone: String?,
    val email: String?,
    val git: String?
)

