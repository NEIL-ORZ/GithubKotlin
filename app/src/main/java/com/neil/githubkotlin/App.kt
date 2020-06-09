package com.neil.githubkotlin

import android.app.Application
import android.content.ContextWrapper

/**
 * @USER NEIL.Z
 * @TIME 2020/6/9 0009 15:01
 * @DESC
 */
private lateinit var INSTANCE: Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}

object AppContext : ContextWrapper(INSTANCE)