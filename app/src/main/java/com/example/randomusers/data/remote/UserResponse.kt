package com.example.randomusers.data.remote

import com.example.randomusers.data.model.User

data class UserResponse(
    val results: List<UserDto>,
    val info: Info
)

data class UserDto(
    val gender: String?,
    val name: Name?,
    val location: Location?,
    val email: String?,
    val login: Login,
    val dob: DateInfo?,
    val registered: DateInfo?,
    val phone: String?,
    val cell: String?,
    val id: Id?,
    val picture: Picture?,
    val nat: String?
) {
    fun toUser(): User {
        return User(
            id = login.uuid,
            name = name?.let { "${name.first} ${name.last}" },
            email = email,
            phone = phone,
            address = "${location?.country}, ${location?.city}, ${location?.street?.name}, ${location?.street?.number}",
            picture = picture?.large,
            gender = gender,
            postcode = location?.postcode,
            coordinates = "${location?.coordinates?.latitude}, ${location?.coordinates?.longitude}",
            timezone = location?.timezone?.offset,
            login = login.username,
            cell = cell,
            birthDay = dob?.date,
            registered = dob?.date,
            age = dob?.age,
            nat = nat
        )
    }
}

data class Name(
    val title: String?,
    val first: String?,
    val last: String?
)

data class Location(
    val street: Street?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: String?,
    val coordinates: Coordinates?,
    val timezone: Timezone?
)

data class Street(
    val number: Int?,
    val name: String?
)

data class Coordinates(
    val latitude: String?,
    val longitude: String?
)

data class Timezone(
    val offset: String?,
    val description: String?
)

data class Login(
    val uuid: String,
    val username: String?,
    val password: String?,
    val salt: String?,
    val md5: String?,
    val sha1: String?,
    val sha256: String?
)

data class DateInfo(
    val date: String?,
    val age: Int?
)

data class Id(
    val name: String?,
    val value: String?
)

data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
)

data class Info(
    val seed: String?,
    val results: Int?,
    val page: Int?,
    val version: String?
)