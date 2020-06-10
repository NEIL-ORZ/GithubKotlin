package com.neil.githubkotlin.model.account

import com.google.gson.Gson
import com.neil.githubkotlin.network.entities.AuthorizationReq
import com.neil.githubkotlin.network.entities.AuthorizationRsp
import com.neil.githubkotlin.network.entities.User
import com.neil.githubkotlin.network.services.AuthService
import com.neil.githubkotlin.network.services.UserService
import com.neil.githubkotlin.utils.fromJson
import com.neil.githubkotlin.utils.pref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

interface OnAccountStateChangeListener {
    fun onLogin(user: User)

    fun onLogout()
}

object AccountManager {
    var authId by pref(-1)
    var username by pref("")
    var passwd by pref("")
    var token by pref("")

    private var userJson by pref("")

    //用户信息
    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson)
            }
            return field
        }
        set(value) {
            if (value == null) {
                userJson = ""
            } else {
                userJson = Gson().toJson(value)
            }
            field = value
        }

    val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach { it.onLogout() }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    //登录
    fun login() =
        AuthService.createAuth(AuthorizationReq())
            .doOnNext {
                //token为空，已经登录，需重新登录，抛出异常
                if (it.token.isEmpty()) throw AccountException(it)
            }
            .retryWhen {
                //登录异常时
                it.flatMap {
                    if (it is AccountException) {
                        //接收异常，注销
                        AuthService.deleteAuth(it.authorizationRsp.id)
                    } else {
                        //其他异常
                        Observable.error(it)
                    }
                }
            }
            .flatMap {
                //登录成功
                token = it.token
                authId = it.id
                //获取用户信息
                UserService.getAuthUser()
            }
            .map {
                //获取用户信息成功
                currentUser = it
                notifyLogin(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    //注销
    fun logout() = AuthService.deleteAuth(authId)
        .doOnNext {
            if (it.isSuccessful) {
                authId = -1
                token = ""
                currentUser = null
                notifyLogout()
            } else {
                throw HttpException(it)
            }
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")
}