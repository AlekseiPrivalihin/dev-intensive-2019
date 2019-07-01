package ru.skillbranch.dev_intensive.models

import ru.skillbranch.dev_intensive.utils.Utils
import java.util.*

data class User (
      val id: String?
    , val firstName: String?
    , val lastName: String?
    , val avatar: String?
    , var rating: Int = 0
    , var respect: Int = 0
    , val lastVisit: Date? = null
    , val isOnline: Boolean = false
) {
    constructor(id: String, firstName: String?, lastName: String?) : this(id, firstName, lastName, null)

    constructor(id: String): this(id, "John", "Doe")

    init {
        println("It's alive!!!\n"
                + "${if (lastName == "Doe") "His name is $firstName $lastName" else "And his name is $firstName $lastName"}!!!\n"
        )
    }

    companion object Factory {
        var lastId = -1

        fun makeUser(fullName: String?): User {
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User("$lastId", firstName, lastName)
        }
    }
}