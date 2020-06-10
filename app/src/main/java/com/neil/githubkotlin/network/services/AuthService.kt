package com.neil.githubkotlin.network.services

import com.neil.githubkotlin.network.entities.AuthorizationReq
import com.neil.githubkotlin.network.entities.AuthorizationRsp
import com.neil.githubkotlin.network.retrofit
import com.neil.githubkotlin.settings.Configs
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * @USER NEIL.Z
 * @TIME 2020/6/10 0010 14:07
 * @DESC
 */
interface AuthApi {

    //登录
    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerprint}")
    fun createAuth(
        @Body req: AuthorizationReq,
        @Path("fingerprint") fingerprint: String = Configs.Account.fingerPrint
    ): Observable<AuthorizationRsp>

    //注销
    @DELETE("/authorizations/{id}")
    fun deleteAuth(@Path("id") id: Int): Observable<Response<Any>>
}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)