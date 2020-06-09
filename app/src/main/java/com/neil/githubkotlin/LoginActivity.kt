package com.neil.githubkotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

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
    }
}
