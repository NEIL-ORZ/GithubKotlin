package com.neil.githubkotlin.utils

import com.neil.common.sharedpreferences.Preference
import com.neil.githubkotlin.AppContext
import kotlin.reflect.jvm.jvmName

/**
 * Created by benny on 6/23/17.
 */
inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)