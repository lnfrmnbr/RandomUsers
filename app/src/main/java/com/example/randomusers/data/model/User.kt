package com.example.randomusers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String?,
    val picture: String?,
    val phone: String?,
    val address: String?,
    val gender: String? = null,
    val postcode: String? = null,
    val coordinates: String? = null,
    val timezone: String? = null,
    val email: String? = null,
    val login: String? = null,
    val cell: String? = null,
    val birthDay: String? = null,
    val age: Int? = null,
    val registered: String? = null,
    val nat: String? = null
)