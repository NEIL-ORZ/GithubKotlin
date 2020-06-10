package com.neil.mvp

import android.content.res.Configuration
import android.os.Bundle

/**
 * @USER NEIL.Z
 * @TIME 2020/6/9 0009 15:44
 * @DESC
 */
interface ILifecycle {
    fun onCreate(savedInstanceState: Bundle?)
    fun onSaveInstanceState(outState: Bundle)
    fun onViewStateRestored(savedInstanceState: Bundle?)
    fun onConfigurationChanged(newConfig: Configuration)
    fun onStart()
    fun onStop()
    fun onResume()
    fun onPause()
    fun onDestroy()
}