package com.neil.githubkotlin

import com.neil.common.Preference

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