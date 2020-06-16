package com.neil.githubkotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.neil.common.ext.no
import com.neil.common.ext.otherwise
import com.neil.common.ext.yes
import com.neil.githubkotlin.R
import com.neil.githubkotlin.model.account.AccountManager
import com.neil.githubkotlin.model.account.AccountManager.username
import com.neil.githubkotlin.presenter.LoginPresenter
import com.neil.githubkotlin.settings.Settings
import com.neil.mvp.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class LoginActivity : BaseActivity<LoginPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            Settings.email = username.text.toString()
            Settings.pwd = password.text.toString()
            presenter.checkUserName(Settings.email)
                .yes {
                    presenter.checkPwd(Settings.pwd)
                        .yes {
                            presenter.doLogin(Settings.email, Settings.pwd)
                        }.otherwise {
                            showTips(password, "密码不合法")
                        }
                }.otherwise {
                    showTips(username, "账号不合法")
                }
        }

        AccountManager.isLoggedIn().yes {
            onLoginSuccess()
        }
    }

    fun showTips(view: EditText, tips: String) {
        view.error = tips
    }

    fun onLoginStart() {
        loading.animate().setDuration(60000).start()
        loading.visibility = View.VISIBLE
    }

    fun onLoginError(e: Throwable) {
        e.printStackTrace()
        Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show()
        loading.visibility = View.GONE
    }

    fun onLoginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show()
        loading.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun onDataInit(name: String, pwd: String) {
        username.setText(name)
        password.setText(pwd)
    }
}
