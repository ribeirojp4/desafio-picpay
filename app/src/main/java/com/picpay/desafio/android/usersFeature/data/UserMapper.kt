package com.picpay.desafio.android.usersFeature.data

fun List<UserPayload>.toUsers() = map {
    User(
        uid = it.id,
        name = it.name,
        username = it.username,
        img = it.img
    )
}

fun List<User>.toUsersVO() = map {
    UserVO(
        id = it.uid,
        name = it.name,
        username = it.username,
        img = it.img
    )
}

@JvmName("toUsersVOUserPayload")
fun List<UserPayload>.toUsersVO() = map {
    UserVO(
        id = it.id,
        name = it.name,
        username = it.username,
        img = it.img
    )
}
