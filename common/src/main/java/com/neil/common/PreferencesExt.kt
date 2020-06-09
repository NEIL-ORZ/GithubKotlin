package com.neil.common

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @USER NEIL.Z
 * @TIME 2020/6/9 0009 14:15
 * @DESC
 */
//Preference扩展
class Preference<T>(
    val context: Context,
    val name: String,
    val default: T,
    val prefName: String = "default"
) : ReadWriteProperty<Any?, T> {

    //属性延迟初始化
    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun putPreference(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("UnSupported type")
            }
        }.apply()
    }

    private fun findPreference(key: String): T {
        return with(prefs) {
            when (default) {
                is Long -> getLong(key, default)
                is String -> getString(key, default)
                else -> throw IllegalArgumentException("UnSupported type")
            }
        } as T
    }

}