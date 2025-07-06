package com.example.randomusers.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.randomusers.data.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val gender: String?,
    val address: String?,
    val coordinates: String?,
    val timezone: String?,
    val email: String?,
    val login: String?,
    val postcode: String?,
    val phone: String?,
    val cell: String?,
    val birthDay: String?,
    val age: Int?,
    val registered: String?,
    val picture: String?,
    val nat: String?
) {
    fun toUser() = User(
        id = id,
        name = name,
        picture = picture,
        phone = phone,
        address = address,
        gender = gender,
        postcode = postcode,
        coordinates = coordinates,
        timezone = timezone,
        email = email,
        login = login,
        cell = cell,
        birthDay = birthDay,
        age = age,
        registered = registered,
        nat = nat
    )

    companion object {
        fun fromUser(user: User) = UserEntity(
            id = user.id,
            name = user.name,
            gender = user.gender,
            address = user.address,
            coordinates = user.coordinates,
            timezone = user.timezone,
            email = user.email,
            login = user.login,
            postcode = user.postcode,
            phone = user.phone,
            cell = user.cell,
            birthDay = user.birthDay,
            age = user.age,
            registered = user.registered,
            picture = user.picture,
            nat = user.nat
        )
    }
}

data class UserSimpleInfo(
    val id: String,
    val name: String?,
    val address: String?,
    val phone: String?,
    val picture: String?
) {
    fun toUserSimpleInfo() = User(
        id = id,
        name = name,
        picture = picture,
        phone = phone,
        address = address
    )
}