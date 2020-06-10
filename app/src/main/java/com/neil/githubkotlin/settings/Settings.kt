package com.neil.githubkotlin.settings

import com.neil.common.sharedpreferences.Preference
import com.neil.githubkotlin.AppContext

/**
 * @USER NEIL.Z
 * @TIME 2020/6/9 0009 14:32
 * @DESC
 */
object Settings {
    //属性代理获取值
    var email: String by Preference(AppContext, "email", "")
    var pwd: String by Preference(AppContext, "pwd", "")
}