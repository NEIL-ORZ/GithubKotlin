package com.neil.githubkotlin.presenter

import com.neil.githubkotlin.model.account.AccountManager
import com.neil.githubkotlin.view.LoginActivity
import com.neil.mvp.impl.BasePresenter

/**
 * @USER NEIL.Z
 * @TIME 2020/6/10 0010 15:09
 * @DESC
 */
class LoginPresenter : BasePresenter<LoginActivity>() {

    fun doLogin(name: String, pwd: String) {
        AccountManager.username = name
        AccountManager.passwd = pwd
        view.onLoginStart()
        AccountManager.login()
            .subscribe({
                view.onLoginSuccess()
            }, {
                view.onLoginError(it)
            })
    }

    fun checkUserName(name: String): Boolean {
        return true
    }

    fun checkPwd(pwd: String): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        view.onDataInit(AccountManager.username, AccountManager.passwd)
    }
}