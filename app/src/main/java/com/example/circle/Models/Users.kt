package com.example.circle.Models

data class Users(
    val profilepic: String?=null,
    val userName: String?=null,
    val mail: String?=null,
    val password: String?=null,
    var userId: String?=null,
    val lastMessage: String?=null
)