package com.neil.githubkotlin.network.services

import com.neil.githubkotlin.network.entities.User
import com.neil.githubkotlin.network.retrofit
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * @USER NEIL.Z
 * @TIME 2020/6/10 0010 14:22
 * @DESC
 */
interface UserApi {

    //用户信息
    @GET("/user")
    fun getAuthUser(): Observable<User>
}

object UserService : UserApi by retrofit.create(UserApi::class.java)