package com.example.lookspot.extras.classes

import com.example.lookspot.extras.models.User

object UserManager {
    private lateinit var user: User

    fun setUser(user:User){
        this.user = user
    }

    fun getUser(): User{
        return user
    }
}




