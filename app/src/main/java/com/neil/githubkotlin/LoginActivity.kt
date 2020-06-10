package com.neil.githubkotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.neil.mvp.impl.mainFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username.setText(Settings.email)
        password.setText(Settings.pwd)

        login.setOnClickListener {
            Settings.email = username.text.toString()
            Settings.pwd = password.text.toString()
            finish()
        }

        val mainFragment = mainFragment()
        Log.e("zh", mainFragment.toString())
        Log.e("zh", mainFragment.presenter.toString())
        Log.e("zh", mainFragment.presenter.view.toString())
    }
}
